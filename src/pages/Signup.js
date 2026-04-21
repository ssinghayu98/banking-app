import { useState } from "react";

function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSignup = async () => {
    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username.trim(),
          password: password.trim(),
        }),
      });

      const data = await response.text();

      if (!response.ok) {
        throw new Error(data);
      }

      setMessage("✅ Signup successful! Please login.");

      setTimeout(() => {
        window.location.href = "/";
      }, 1500);

    } catch (error) {
      console.error(error);
      setMessage("❌ Signup failed");
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>📝 Create Account</h2>

        <input
          type="text"
          placeholder="Username"
          style={styles.input}
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          style={styles.input}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button style={styles.button} onClick={handleSignup}>
          Sign Up
        </button>

        <p style={styles.message}>{message}</p>

        <p style={styles.link} onClick={() => (window.location.href = "/")}>
          Already have an account? Login
        </p>
      </div>
    </div>
  );
}

// 🎨 Styles
const styles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "linear-gradient(to right, #43cea2, #185a9d)",
  },
  card: {
    background: "white",
    padding: "40px",
    borderRadius: "15px",
    textAlign: "center",
    width: "300px",
  },
  input: {
    width: "100%",
    padding: "10px",
    margin: "10px 0",
    borderRadius: "8px",
    border: "1px solid #ccc",
  },
  button: {
    width: "100%",
    padding: "10px",
    background: "#43cea2",
    border: "none",
    color: "white",
    borderRadius: "8px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  message: {
    marginTop: "10px",
  },
  link: {
    marginTop: "10px",
    color: "#185a9d",
    cursor: "pointer",
    fontWeight: "bold",
  },
};

export default Signup;