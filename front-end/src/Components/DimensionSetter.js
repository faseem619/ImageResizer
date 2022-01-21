import React from "react";

import "../CSS/setter.css";

function DimensionSetter({ setHeight, setWidth }) {
  return (
    <div className="setter_container">
      <div className="setter">
        <div>
          <h1>Enter The New Dimensions</h1>
        </div>
        <form className="setter--form">
          <div className="height-section dimension_section">
            <input
              type="number"
              placeholder="Height"
              required
              onChange={(e) => setHeight(e.target.value)}
            />
          </div>
          <div className="width-section dimension_section">
            <input
              type="number"
              placeholder="Width"
              required
              onChange={(e) => setHeight(e.target.value)}
            />
          </div>
          <div className="button submit_button">
            <button>Resize Image</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default DimensionSetter;
