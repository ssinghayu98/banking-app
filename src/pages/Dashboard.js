import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import Navbar from "../components/Navbar";

function Dashboard() {

  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState("");
  const [username, setUsername] = useState("");
  const [message, setMessage] = useState("");
  const [transactions, setTransactions] = useState([]);
  const [showTransactions, setShowTransactions] = useState(false);

  const token = localStorage.getItem("token");

  // 🔄 Fetch Data
  const fetchData = async () => {
    try {
      const decoded = jwtDecode(token);
      const user = decoded.sub;
      setUsername(user);

      // Balance
      const res1 = await fetch(`http://localhost:8080/user/balance/${user}`);
      const data1 = await res1.json();
      setBalance(data1.balance);

      // Transactions
      const res2 = await fetch(`http://localhost:8080/user/transactions/${user}`);
      const data2 = await res2.json();

      setTransactions(Array.isArray(data2) ? data2 : []);

    } catch (err) {
      console.error(err);
      setMessage("❌ Error loading data");
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  // 💰 Deposit
  const handleDeposit = async () => {
    try {
      const decoded = jwtDecode(token);
      const user = decoded.sub;

      const res = await fetch(
        `http://localhost:8080/user/deposit?username=${user}&amount=${amount}`,
        { method: "POST" }
      );

      if (!res.ok) throw new Error();

      setMessage("✅ Deposit successful");
      setAmount("");
      fetchData();

    } catch {
      setMessage("❌ Deposit failed");
    }
  };

  // 💸 Withdraw
  const handleWithdraw = async () => {
    try {
      const decoded = jwtDecode(token);
      const user = decoded.sub;

      const res = await fetch(
        `http://localhost:8080/user/withdraw?username=${user}&amount=${amount}`,
        { method: "POST" }
      );

      if (!res.ok) throw new Error();

      setMessage("✅ Withdraw successful");
      setAmount("");
      fetchData();

    } catch {
      setMessage("❌ Insufficient balance");
    }
  };

  // 🚪 Logout
  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <>
      <Navbar />

      <div style={styles.container}>
        <div style={styles.card}>

          <h2>🏦 Banking Dashboard</h2>

          <p>Welcome, <b>{username}</b></p>

          <h1 style={styles.balance}>₹{balance}</h1>

          <input
            type="number"
            placeholder="Enter amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            style={styles.input}
          />

          <button style={styles.deposit} onClick={handleDeposit}>
            💰 Deposit
          </button>

          <button style={styles.withdraw} onClick={handleWithdraw}>
            💸 Withdraw
          </button>

          <button style={styles.refresh} onClick={fetchData}>
            🔄 Refresh Balance
          </button>

          <button style={styles.logout} onClick={logout}>
            🚪 Logout
          </button>

          {/* Message */}
          {message && <p style={styles.message}>{message}</p>}

          {/* 🔘 Toggle Button */}
          <button
            style={styles.transactionsBtn}
            onClick={() => setShowTransactions(!showTransactions)}
          >
            📊 {showTransactions ? "Hide Transactions" : "Show Transactions"}
          </button>

          {/* 📊 Transactions List */}
          {showTransactions && (
            <ul style={styles.list}>
              {Array.isArray(transactions) &&
                transactions.map((t, i) => (
                  <li key={i}>{t}</li>
                ))}
            </ul>
          )}

        </div>
      </div>
    </>
  );
}

// 🎨 Styles
const styles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "linear-gradient(to right, #667eea, #764ba2)",
  },
  card: {
    background: "white",
    padding: "30px",
    borderRadius: "15px",
    width: "350px",
    textAlign: "center",
    boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
  },
  balance: {
    margin: "20px 0",
    fontSize: "28px",
  },
  input: {
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    borderRadius: "8px",
    border: "1px solid #ccc",
  },
  deposit: {
    width: "100%",
    marginTop: "10px",
    padding: "10px",
    background: "green",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
  withdraw: {
    width: "100%",
    marginTop: "10px",
    padding: "10px",
    background: "orange",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
  refresh: {
    width: "100%",
    marginTop: "10px",
    padding: "10px",
    background: "#4facfe",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
  logout: {
    width: "100%",
    marginTop: "10px",
    padding: "10px",
    background: "red",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
  transactionsBtn: {
    width: "100%",
    marginTop: "15px",
    padding: "10px",
    background: "#6c5ce7",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
  message: {
    marginTop: "12px",
    fontWeight: "bold",
  },
  list: {
    textAlign: "left",
    marginTop: "10px",
  },
};

export default Dashboard;