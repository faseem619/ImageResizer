import React, { useEffect, useState } from "react";
import "./App.css";
import DimensionSetter from "./Components/DimensionSetter";
import DragArea from "./Components/DragArea";
import Navbar from "./Components/Navbar";

function App() {
  const [height, setHeight] = useState("");
  const [width, setWidth] = useState("");
  const [file, setFile] = useState("");
  return (
    <div className="App">
      <Navbar />
      <DragArea
        setWidth={setWidth}
        setHeight={setHeight}
        setFile={setFile}
        file={file}
      />
      <DimensionSetter
        setHeight={setHeight}
        setWidth={setWidth}
        file={file}
        width={width}
        height={height}
      />
      hello world
    </div>
  );
}

export default App;
