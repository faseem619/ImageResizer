import React from "react";
import "../CSS/dragarea.css";
import SelectButton from "./SelectButton";
function DragArea() {
  return (
    <div className="drag_area--container">
      <div className="drag_area">
        <i className="fas fa-image"></i>
        <p>Drop Your Image Here!</p>
        <SelectButton />
      </div>
    </div>
  );
}

export default DragArea;
