import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await axios.post("http://localhost:8080/auth/login", {
        username,
        password
      });

      // ✅ Save username (no JWT for now)
      localStorage.setItem("username", username);

      // ✅ Go to dashboard
      navigate("/dashboard");
    } catch (err) {
      setError("Invalid username or password");
    }
  };

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "linear-gradient(135deg, #667eea, #764ba2)",
        fontFamily: "Segoe UI"
      }}
    >
      <div
        style={{
          background: "#fff",
          padding: "35px",
          borderRadius: "15px",
          width: "340px",
          boxShadow: "0 15px 35px rgba(0,0,0,0.2)",
          textAlign: "center"
        }}
      >
        <h2 style={{ marginBottom: "5px" }}>🏦 Banking App</h2>

        <p style={{ fontSize: "13px", color: "#777" }}>
          Secure Digital Banking System
        </p>

        <form onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="👤 Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{
              width: "100%",
              padding: "12px",
              margin: "12px 0",
              borderRadius: "8px",
              border: "1px solid #ccc"
            }}
          />

          <input
            type="password"
            placeholder="🔒 Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{
              width: "100%",
              padding: "12px",
              margin: "12px 0",
              borderRadius: "8px",
              border: "1px solid #ccc"
            }}
          />

          <button
            type="submit"
            style={{
              width: "100%",
              padding: "12px",
              background: "#4facfe",
              color: "#fff",
              border: "none",
              borderRadius: "8px",
              fontSize: "16px",
              cursor: "pointer",
              marginTop: "5px"
            }}
          >
            Login
          </button>
        </form>

        {/* Register link */}
        <p style={{ marginTop: "15px", fontSize: "14px" }}>
          Don't have an account?{" "}
          <span
            onClick={() => navigate("/register")}
            style={{ color: "#4facfe", cursor: "pointer", fontWeight: "bold" }}
          >
            Sign Up
          </span>
        </p>

        {/* Error */}
        {error && (
          <p
            style={{
              color: "red",
              marginTop: "10px",
              fontSize: "14px"
            }}
          >
            ❌ {error}
          </p>
        )}
      </div>
    </div>
  );
}

export default Login;