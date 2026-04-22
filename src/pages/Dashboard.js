import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState("");
  const [transactions, setTransactions] = useState([]); // ✅ FIXED
  const [message, setMessage] = useState("");

  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  // ==============================
  // 🔥 LOAD BALANCE
  // ==============================
  const loadBalance = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/user/balance?username=${username}`
      );

      console.log("BALANCE RESPONSE:", res.data);

      setBalance(res.data.data || 0);
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to load balance");
    }
  };

  // ==============================
  // 🔥 LOAD TRANSACTIONS
  // ==============================
  const loadTransactions = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/user/transactions?username=${username}`
      );

      console.log("TRANSACTION RESPONSE:", res.data);

      setTransactions(res.data.data || []); // ✅ CRITICAL FIX
    } catch (err) {
      console.error(err);
      setTransactions([]); // ✅ NEVER UNDEFINED
      setMessage("❌ Failed to load transactions");
    }
  };

  // ==============================
  // 🔥 DEPOSIT
  // ==============================
  const deposit = async () => {
    if (!amount || amount <= 0) {
      setMessage("❌ Enter valid amount");
      return;
    }

    try {
      await axios.post(
        `http://localhost:8080/user/deposit?username=${username}&amount=${amount}`
      );

      setMessage("✅ Deposit successful");
      setAmount("");

      loadBalance();
      loadTransactions();
    } catch (err) {
      console.error(err);
      setMessage("❌ Deposit failed");
    }
  };

  // ==============================
  // 🔥 WITHDRAW
  // ==============================
  const withdraw = async () => {
    if (!amount || amount <= 0) {
      setMessage("❌ Enter valid amount");
      return;
    }

    try {
      await axios.post(
        `http://localhost:8080/user/withdraw?username=${username}&amount=${amount}`
      );

      setMessage("✅ Withdraw successful");
      setAmount("");

      loadBalance();
      loadTransactions();
    } catch (err) {
      console.error(err);
      setMessage("❌ Withdraw failed");
    }
  };

  // ==============================
  // 🔥 LOGOUT
  // ==============================
  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  // ==============================
  // 🔥 ON LOAD
  // ==============================
  useEffect(() => {
    if (!username) {
      navigate("/");
    } else {
      loadBalance();
      loadTransactions();
    }
  }, []);

  // ==============================
  // 🎨 UI
  // ==============================
  return (
    <div
      style={{
        minHeight: "100vh",
        background: "linear-gradient(135deg, #4facfe, #6a11cb)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        fontFamily: "Segoe UI"
      }}
    >
      <div
        style={{
          width: "400px",
          background: "#fff",
          padding: "30px",
          borderRadius: "15px",
          boxShadow: "0 10px 30px rgba(0,0,0,0.2)",
          textAlign: "center"
        }}
      >
        <h2>🏦 Banking Dashboard</h2>

        <p style={{ fontSize: "12px", color: "#888" }}>
          Secure Digital Banking System
        </p>

        <p>
          Welcome, <b>{username}</b>
        </p>

        <h1 style={{ margin: "15px 0", color: "#2c3e50" }}>
          ₹{balance}
        </h1>

        {/* INPUT */}
        <input
          type="number"
          placeholder="Enter amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          style={{
            width: "100%",
            padding: "12px",
            borderRadius: "8px",
            border: "1px solid #ccc",
            marginBottom: "10px"
          }}
        />

        {/* BUTTONS */}
        <button
          onClick={deposit}
          style={{
            width: "100%",
            padding: "10px",
            background: "#2ecc71",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            marginBottom: "8px",
            cursor: "pointer"
          }}
        >
          Deposit
        </button>

        <button
          onClick={withdraw}
          style={{
            width: "100%",
            padding: "10px",
            background: "#f39c12",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            marginBottom: "8px",
            cursor: "pointer"
          }}
        >
          Withdraw
        </button>

        <button
          onClick={loadBalance}
          style={{
            width: "100%",
            padding: "10px",
            background: "#3498db",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            cursor: "pointer"
          }}
        >
          Refresh Balance
        </button>

        <button
          onClick={logout}
          style={{
            width: "100%",
            padding: "10px",
            background: "red",
            color: "#fff",
            border: "none",
            borderRadius: "8px",
            marginTop: "10px",
            cursor: "pointer"
          }}
        >
          Logout
        </button>

        {/* MESSAGE */}
        {message && (
          <p
            style={{
              marginTop: "12px",
              color: message.includes("❌") ? "red" : "green",
              fontWeight: "bold"
            }}
          >
            {message}
          </p>
        )}

        <hr style={{ margin: "20px 0" }} />

        {/* TRANSACTIONS */}
        <h3>Transaction History</h3>

        {transactions && transactions.length === 0 ? (
          <p style={{ color: "#777" }}>No transactions yet</p>
        ) : (
          transactions.map((t, i) => (
            <div
              key={i}
              style={{
                display: "flex",
                justifyContent: "space-between",
                padding: "8px",
                borderBottom: "1px solid #eee"
              }}
            >
              <span>{t.type}</span>
              <span>₹{t.amount}</span>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default Dashboard;