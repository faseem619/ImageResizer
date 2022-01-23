import React, { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";

import "../CSS/dragarea.css";
import SelectButton from "./SelectButton";

function DragArea({ setHeight, setWidth, setFile, file, setId }) {
  const dragArea = useRef(null);

  let [images, setImages] = useState([]);

  const handleDrop = (e) => {
    e.preventDefault();
    dragArea.current.classList.remove("drag_area--active");
    if (e.dataTransfer.files[0].type.includes("image"))
      setFile(e.dataTransfer.files[0]);
  };
  const handleDragOver = (e) => {
    e.preventDefault();
    dragArea.current.classList.add("drag_area--active");
  };

  const handleDragLeave = (e) => {
    e.preventDefault();
    dragArea.current.classList.remove("drag_area--active");
  };
  useEffect(() => {
    if (file !== "") {
      setId(uuidv4);
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        setImages([...images, reader.result]);
        let image = new Image();
        image.src = reader.result;
        image.onload = () => {
          setWidth(image.width);
          setHeight(image.height);
        };
      };
    }
  }, [file]);

  return (
    <div className="drag_area--container">
      <div
        className="drag_area"
        onDrop={handleDrop}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        ref={dragArea}
      >
        {images.map((imageUrl) => (
          <img
            src={imageUrl}
            alt="file failed to load"
            className="preview-image"
            key={imageUrl.toString()}
          ></img>
        ))}
        <i className="fas fa-image"></i>
        <p>Drop Your Image Here!</p>
        <SelectButton />
      </div>
    </div>
  );
}

export default DragArea;
