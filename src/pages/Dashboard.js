import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState("");
  const [message, setMessage] = useState("");
  const [transactions, setTransactions] = useState([]);

  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  const navigate = useNavigate();

  // ===============================
  // ✅ LOAD DATA
  // ===============================
  useEffect(() => {
    fetchBalance();
    fetchTransactions();
  }, []);

  const fetchBalance = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/user/balance",
        {
          params: { username },
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setBalance(res.data.data || res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchTransactions = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/user/transactions",
        {
          params: { username },
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setTransactions(res.data.data || []);
    } catch (err) {
      console.error(err);
    }
  };

  // ===============================
  // ✅ DEPOSIT
  // ===============================
  const deposit = async () => {
    try {
      await axios.post(
        "http://localhost:8080/user/deposit",
        null,
        {
          params: { username, amount },
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMessage("✅ Deposit successful");
      fetchBalance();
      fetchTransactions();
    } catch {
      setMessage("❌ Deposit failed");
    }
  };

  // ===============================
  // ✅ WITHDRAW
  // ===============================
  const withdraw = async () => {
    try {
      await axios.post(
        "http://localhost:8080/user/withdraw",
        null,
        {
          params: { username, amount },
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMessage("✅ Withdraw successful");
      fetchBalance();
      fetchTransactions();
    } catch {
      setMessage("❌ Withdraw failed");
    }
  };

  // ===============================
  // ✅ LOGOUT
  // ===============================
  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div style={styles.container}>
      <div style={styles.navbar}>
        <h3>🏦 Banking App</h3>
        <button onClick={logout} style={styles.logout}>
          Logout
        </button>
      </div>

      <div style={styles.card}>
        <h2>Banking Dashboard</h2>
        <p>Welcome, <b>{username}</b></p>

        <h1>₹{balance}</h1>

        <input
          placeholder="Enter amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          style={styles.input}
        />

        <button onClick={deposit} style={styles.deposit}>
          Deposit
        </button>

        <button onClick={withdraw} style={styles.withdraw}>
          Withdraw
        </button>

        <button onClick={fetchBalance} style={styles.refresh}>
          Refresh Balance
        </button>

        <p>{message}</p>

        <h3>Transaction History</h3>

        {transactions.length === 0 ? (
          <p>No transactions yet</p>
        ) : (
          transactions.map((tx, index) => (
            <div key={index} style={styles.tx}>
              <p>
                {tx.type} - ₹{tx.amount}
              </p>
              <small>{tx.timestamp}</small>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

const styles = {
  container: {
    minHeight: "100vh",
    background: "linear-gradient(to right, #6a11cb, #2575fc)",
  },
  navbar: {
    display: "flex",
    justifyContent: "space-between",
    padding: "15px",
    color: "white",
  },
  logout: {
    background: "red",
    color: "white",
    border: "none",
    padding: "10px",
    cursor: "pointer",
  },
  card: {
    background: "white",
    width: "350px",
    margin: "50px auto",
    padding: "20px",
    borderRadius: "10px",
    textAlign: "center",
  },
  input: {
    width: "100%",
    padding: "10px",
    margin: "10px 0",
  },
  deposit: {
    width: "100%",
    padding: "10px",
    background: "green",
    color: "white",
    marginTop: "5px",
  },
  withdraw: {
    width: "100%",
    padding: "10px",
    background: "orange",
    color: "white",
    marginTop: "5px",
  },
  refresh: {
    width: "100%",
    padding: "10px",
    background: "blue",
    color: "white",
    marginTop: "5px",
  },
  tx: {
    borderBottom: "1px solid #ccc",
    marginTop: "10px",
  },
};

export default Dashboard;