import { useState } from "react";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleLogin = async () => {
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username.trim(),
          password: password.trim(),
        }),
      });

      const data = await response.json();
      console.log("LOGIN RESPONSE:", data);

      // ✅ Since backend is fixed, token will be here
      if (!data.token) {
        setMessage("❌ Invalid Username or Password");
        return;
      }

      // 🔐 Save JWT
      localStorage.setItem("token", data.token);

      // ✅ Success message
      setMessage("✅ Login Successful!");

      // 🔁 Redirect to dashboard
      setTimeout(() => {
        window.location.href = "/dashboard";
      }, 1000);

    } catch (error) {
      console.error("LOGIN ERROR:", error);
      setMessage("❌ Server error");
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>🏦 Banking App Login</h2>

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

        <button style={styles.button} onClick={handleLogin}>
          Login
        </button>

        <p style={styles.message}>{message}</p>
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
    background: "linear-gradient(to right, #4facfe, #00f2fe)",
  },
  card: {
    background: "white",
    padding: "40px",
    borderRadius: "15px",
    textAlign: "center",
    boxShadow: "0 10px 25px rgba(0,0,0,0.2)",
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
    background: "#4facfe",
    border: "none",
    color: "white",
    borderRadius: "8px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  message: {
    marginTop: "15px",
    fontWeight: "bold",
  },
};

export default Login;