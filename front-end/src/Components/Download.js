import React from "react";
import { GetImage } from "../ApiOperations";

function Download({ id }) {
  const handleDownload = () => {
    console.log(id);
    GetImage(id);
  };
  return (
    <div>
      <button onClick={handleDownload}>Download</button>{" "}
    </div>
  );
}

export default Download;
