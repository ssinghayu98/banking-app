import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";

function Dashboard() {
  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState("");
  const [username, setUsername] = useState("");

  const token = localStorage.getItem("token");

  // ✅ Fetch balance
  const fetchBalance = async () => {
    try {
      const decoded = jwtDecode(token);
      const user = decoded.sub;
      setUsername(user);

      const res = await fetch(
        `http://localhost:8080/user/balance/${user}`
      );

      const data = await res.json();
      setBalance(data.balance);

    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchBalance();
  }, []);

  // 💰 Deposit
  const handleDeposit = async () => {
    await fetch(
      `http://localhost:8080/user/deposit?userId=1&amount=${amount}`,
      { method: "POST" }
    );

    fetchBalance();
  };

  // 💸 Withdraw
  const handleWithdraw = async () => {
    await fetch(
      `http://localhost:8080/user/withdraw?userId=1&amount=${amount}`,
      { method: "POST" }
    );

    fetchBalance();
  };

  // 🚪 Logout
  const logout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>🏦 Banking Dashboard</h2>
        <p>Welcome, {username}</p>

        <h1 style={styles.balance}>₹{balance}</h1>

        <input
          type="number"
          placeholder="Enter amount"
          style={styles.input}
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <button style={styles.deposit} onClick={handleDeposit}>
          💰 Deposit
        </button>

        <button style={styles.withdraw} onClick={handleWithdraw}>
          💸 Withdraw
        </button>

        <button style={styles.refresh} onClick={fetchBalance}>
          🔄 Refresh Balance
        </button>

        <button style={styles.logout} onClick={logout}>
          🚪 Logout
        </button>
      </div>
    </div>
  );
}

// 🎨 STYLES
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
    padding: "40px",
    borderRadius: "15px",
    textAlign: "center",
    width: "320px",
    boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
  },
  balance: {
    margin: "20px 0",
  },
  input: {
    width: "100%",
    padding: "10px",
    borderRadius: "8px",
    marginTop: "10px",
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
};

export default Dashboard;