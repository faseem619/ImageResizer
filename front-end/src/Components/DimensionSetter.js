import React from "react";
import { PostImage } from "../ApiOperations";

import "../CSS/setter.css";

function DimensionSetter({ setHeight, setWidth, file, width, height, id }) {
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (file !== "") {
      const formData = new FormData();
      formData.append("image", file);
      formData.append("height", height);
      formData.append("width", width);
      formData.append("id", id);
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
              value={width}
              onChange={(e) => setWidth(e.target.value)}
            />
            <label className="label">Width</label>
          </div>
          <div className="height-section dimension_section">
            <input
              type="number"
              placeholder="Height"
              required
              value={height}
              onChange={(e) => setHeight(e.target.value)}
            />
            <label className="label">Height</label>
          </div>

          <button className="button submit_button">Resize Image</button>
        </form>
      </div>
    </div>
  );
}

export default DimensionSetter;
