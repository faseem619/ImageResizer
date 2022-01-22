import React from "react";
import { PostImage } from "../ApiOperations";

import "../CSS/setter.css";

function DimensionSetter({ setHeight, setWidth, file, width, height }) {
  const handleSubmit = (e) => {
    e.preventDefault();
    if (file !== "") {
      const formData = new FormData();
      formData.append("image", file);
      formData.append("height", height);
      formData.append("width", width);
      PostImage(formData);
    }
  };
  return (
    <div className="setter_container">
      <div className="setter">
        <div>
          <h1>Enter The New Dimensions</h1>
        </div>
        <form className="setter--form" onSubmit={handleSubmit}>
          <div className="width-section dimension_section">
            <input
              type="number"
              placeholder="Width"
              required
              onChange={(e) => setWidth(e.target.value)}
            />
          </div>
          <div className="height-section dimension_section">
            <input
              type="number"
              placeholder="Height"
              required
              onChange={(e) => setHeight(e.target.value)}
            />
          </div>

          <button className="button submit_button">Resize Image</button>
        </form>
      </div>
    </div>
  );
}

export default DimensionSetter;
