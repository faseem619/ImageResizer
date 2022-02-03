import React from "react";
import "../CSS/button.css";

function SelectButton({ updateImage, setFile, setImageType }) {
  const handleFileUpload = () => {
    var x = document.createElement("INPUT");
    x.setAttribute("type", "file");
    x.click();
    x.addEventListener("change", (e) => {
      setFile(e.target.files[0]);
      updateImage(e.target.files[0]);
      setImageType(e.dataTransfer.files[0].type.split("/")[1]);
    });
  };
  return (
    <div className=" button button--select-file " onClick={handleFileUpload}>
      Select Image
    </div>
  );
}

export default SelectButton;
