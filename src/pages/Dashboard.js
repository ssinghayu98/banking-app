import { useEffect, useState } from "react";

function Dashboard() {
  const [balance, setBalance] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);

  // 🔥 CHANGE THIS USERNAME IF NEEDED
  const username = "admin2"; // ⚠️ must match DB exactly

  const fetchBalance = async () => {
    try {
      setLoading(true);

      const token = localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/user/balance/${username}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`, // JWT
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to fetch balance");
      }

      const data = await response.json();

      setBalance(data.balance);
      setMessage("✅ Data loaded successfully");
    } catch (error) {
      setMessage("❌ Something went wrong");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBalance();
  }, []);

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>🏦 Dashboard</h2>

        {loading ? (
          <p>Loading...</p>
        ) : (
          <>
            <h3 style={styles.balance}>
              {balance !== null ? `₹${balance}` : "No Data"}
            </h3>

            <p>{message}</p>

            <button style={styles.button} onClick={fetchBalance}>
              Refresh Balance
            </button>
          </>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "linear-gradient(to right, #4facfe, #00f2fe)",
  },
  card: {
    background: "white",
    padding: "40px",
    borderRadius: "15px",
    textAlign: "center",
    boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
  },
  balance: {
    fontSize: "28px",
    margin: "20px 0",
  },
  button: {
    padding: "10px 20px",
    border: "none",
    background: "#4facfe",
    color: "white",
    borderRadius: "8px",
    cursor: "pointer",
    fontWeight: "bold",
  },
};

export default Dashboard;