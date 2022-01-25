import "../CSS/navbar.css";

function Navbar() {
  return (
    <div className="navbar">
      <h1>Image Resizer</h1>
      <i
        className="fab fa-github-square"
        onClick={() =>
          (window.location.href = "https://github.com/faseem619/ImageResizer")
        }
      ></i>
    </div>
  );
}

export default Navbar;
