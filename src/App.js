import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 🔐 Login */}
        <Route path="/" element={<Login />} />
<Route path="/register" element={<Register />} />
        {/* 📝 Signup */}
        <Route path="/signup" element={<Signup />} />

        {/* 🏦 Dashboard */}
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;