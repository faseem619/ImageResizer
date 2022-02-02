import React, { useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";

import "../CSS/dragarea.css";
import SelectButton from "./SelectButton";

function DragArea({
  setHeight,
  setWidth,
  setFile,
  file,
  setId,
  setImageType,
  imageType,
}) {
  const dragArea = useRef(null);

  let [images, setImages] = useState("");

  //sets file state to dropped image
  //removes the highlighted effect when hovering over with file
  const handleDrop = (e) => {
    e.preventDefault();
    dragArea.current.classList.remove("drag_area--active");
    if (e.dataTransfer.files[0].type.includes("image")) {
      setFile(e.dataTransfer.files[0]);
      setImageType(e.dataTransfer.files[0].type);
      console.log(imageType);
    }
  };
  // adds selected coloring effect
  const handleDragOver = (e) => {
    e.preventDefault();
    dragArea.current.classList.add("drag_area--active");
  };
  // removes selected coloring effect
  const handleDragLeave = (e) => {
    e.preventDefault();
    dragArea.current.classList.remove("drag_area--active");
  };

  const updateImage = (myFile) => {
    let reader = new FileReader();
    reader.readAsDataURL(myFile);
    reader.onload = function () {
      setImages(reader.result);
      let image = new Image();
      image.src = reader.result;
      image.onload = () => {
        setWidth(image.width);
        setHeight(image.height);
      };
    };
  };
  // creates unique id
  // adds new image thumbnail to drag area of dropped image
  useEffect(() => {
    if (file !== "") {
      setId(uuidv4);
      updateImage(file);
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
        {images && (
          <img
            src={images}
            alt="file failed to load"
            className="preview-image"
          ></img>
        )}
        <i className="fas fa-image"></i>
        <p>Drop Your Image Here!!</p>
        <SelectButton updateImage={updateImage} setFile={setFile} />
      </div>
    </div>
  );
}

export default DragArea;
