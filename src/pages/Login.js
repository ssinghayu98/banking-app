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
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {
          username: username,
          password: password
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );

      // ✅ Save JWT token
      const token = response.data.data;
      localStorage.setItem("token", token);
      localStorage.setItem("username", username);

      // ✅ Redirect to dashboard
      navigate("/dashboard");

    } catch (err) {
      console.error("LOGIN ERROR:", err);

      if (err.response) {
        setError(err.response.data.message || "Login failed");
      } else {
        setError("Server not reachable");
      }
    }
  };

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "linear-gradient(to right, #5f9cff, #6a5acd)"
      }}
    >
      <form
        onSubmit={handleLogin}
        style={{
          background: "#fff",
          padding: "30px",
          borderRadius: "10px",
          width: "300px",
          boxShadow: "0 5px 15px rgba(0,0,0,0.2)"
        }}
      >
        <h2 style={{ textAlign: "center" }}>🏦 Banking App Login</h2>

        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          style={{
            width: "100%",
            padding: "10px",
            margin: "10px 0"
          }}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          style={{
            width: "100%",
            padding: "10px",
            margin: "10px 0"
          }}
        />

        <button
          type="submit"
          style={{
            width: "100%",
            padding: "10px",
            background: "#1976d2",
            color: "#fff",
            border: "none",
            cursor: "pointer"
          }}
        >
          Login
        </button>

        {error && (
          <p style={{ color: "red", marginTop: "10px", textAlign: "center" }}>
            ❌ {error}
          </p>
        )}
      </form>
    </div>
  );
}

export default Login;