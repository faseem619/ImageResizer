import React, { useState } from "react";
import "./App.css";
import DimensionSetter from "./Components/DimensionSetter";
import DragArea from "./Components/DragArea";
import Navbar from "./Components/Navbar";
import Download from "./Components/Download";

function App() {
  const [height, setHeight] = useState("");
  const [width, setWidth] = useState("");
  const [file, setFile] = useState("");
  const [id, setId] = useState("");
  return (
    <div className="App">
      <Navbar />
      <DragArea
        setWidth={setWidth}
        setHeight={setHeight}
        setFile={setFile}
        file={file}
        setId={setId}
      />
      <Download id={id} />
      <DimensionSetter
        setHeight={setHeight}
        setWidth={setWidth}
        file={file}
        width={width}
        height={height}
        id={id}
      />
      hello world
    </div>
  );
}

export default App;
