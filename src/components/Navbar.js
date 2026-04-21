function Navbar() {
  return (
    <div style={styles.nav}>
      <h2>🏦 Banking App</h2>
    </div>
  );
}

const styles = {
  nav: {
    background: "#333",
    color: "white",
    padding: "10px",
    textAlign: "center",
  },
};

export default Navbar;