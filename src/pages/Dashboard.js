import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";

function Dashboard() {
  const [balance, setBalance] = useState(null);
  const [username, setUsername] = useState("");
  const [error, setError] = useState("");

  const fetchBalance = async () => {
    try {
      const token = localStorage.getItem("token");

      // 🔐 Decode JWT
      const decoded = jwtDecode(token);
      const user = decoded.sub;

      setUsername(user);

      const response = await fetch(
        `http://localhost:8080/user/balance/${user}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to fetch balance");
      }

      const data = await response.json();

      setBalance(Number(data.balance));
      setError("");
    } catch (err) {
      setError("❌ Failed to load data");
      console.error(err);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");

    // 🔒 Protect route
    if (!token) {
      window.location.href = "/";
    } else {
      fetchBalance();
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>🏦 Banking Dashboard</h2>

        <p style={styles.welcome}>Welcome, {username}</p>

        {error && <p style={styles.error}>{error}</p>}

        {balance !== null ? (
          <h1 style={styles.balance}>₹{balance}</h1>
        ) : (
          <p>Loading...</p>
        )}

        <button style={styles.button} onClick={fetchBalance}>
          🔄 Refresh Balance
        </button>

        <button style={styles.logout} onClick={handleLogout}>
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
    boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
    width: "300px",
  },
  welcome: {
    marginBottom: "10px",
    fontWeight: "bold",
  },
  balance: {
    fontSize: "32px",
    margin: "20px 0",
  },
  button: {
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    background: "#667eea",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  logout: {
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    background: "red",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  error: {
    color: "red",
    marginTop: "10px",
  },
};

export default Dashboard;