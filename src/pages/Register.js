import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    username: "",
    password: "",
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/auth/register", form);

      setMessage("✅ Registered successfully! Redirecting...");

      // ⏳ redirect after 1.5 sec
      setTimeout(() => {
        navigate("/");
      }, 1500);

    } catch (error) {
      setMessage("❌ Registration failed (username may exist)");
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>🏦 Create Account</h2>

        <form onSubmit={handleRegister}>
          <input
            type="text"
            name="username"
            placeholder="Enter Username"
            value={form.username}
            onChange={handleChange}
            style={styles.input}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Enter Password"
            value={form.password}
            onChange={handleChange}
            style={styles.input}
            required
          />

          <button type="submit" style={styles.button}>
            Register
          </button>
        </form>

        {message && <p style={styles.message}>{message}</p>}

        <p style={styles.linkText}>
          Already have an account?{" "}
          <span onClick={() => navigate("/")} style={styles.link}>
            Login
          </span>
        </p>
      </div>
    </div>
  );
}

export default Register;

const styles = {
  container: {
    height: "100vh",
    background: "linear-gradient(135deg, #4facfe, #6a11cb)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  card: {
    background: "#fff",
    padding: "30px",
    borderRadius: "12px",
    width: "320px",
    textAlign: "center",
    boxShadow: "0 8px 20px rgba(0,0,0,0.2)",
  },
  title: {
    marginBottom: "20px",
  },
  input: {
    width: "100%",
    padding: "10px",
    marginBottom: "15px",
    borderRadius: "6px",
    border: "1px solid #ccc",
  },
  button: {
    width: "100%",
    padding: "10px",
    background: "#1e73be",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  message: {
    marginTop: "10px",
    fontSize: "14px",
  },
  linkText: {
    marginTop: "15px",
    fontSize: "14px",
  },
  link: {
    color: "#1e73be",
    cursor: "pointer",
    fontWeight: "bold",
  },
};