import React, { useEffect, useRef, useState } from "react";

import "../CSS/dragarea.css";
import SelectButton from "./SelectButton";

function DragArea({ height, width }) {
  const dragArea = useRef(null);

  let [file, setFile] = useState("");
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
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        setImages([...images, reader.result]);
      };
      // const PostImage = async (data = {}) => {
      //   await fetch("http://localhost:8080", {
      //     method: "POST",
      //     mode: "cors",
      //     cache: "no-cache",
      //     credentials: "same-origin",
      //     redirect: "follow",
      //     referrerPolicy: "no-referrer",
      //     body: data,
      //   });
      // };
      // const formData = new FormData();
      // formData.append("image", file);
      // formData.append("width", 100);
      // formData.append("height", 200);
      // PostImage(formData);
      setFile("");
    }
  }, [file, images]);

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
