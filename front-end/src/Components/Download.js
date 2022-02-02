import React from "react";
import { GetImage } from "../ApiOperations";

function Download({ id, imageType }) {
  //downloads scaled image
  const handleDownload = () => {
    GetImage(id, imageType);
  };
  return (
    <div>
      <button onClick={handleDownload} className="button submit_button ">
        Download
      </button>
    </div>
  );
}

export default Download;
