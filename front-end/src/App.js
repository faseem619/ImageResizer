import React, { useEffect, useState } from "react";
import "./App.css";
import DimensionSetter from "./Components/DimensionSetter";
import DragArea from "./Components/DragArea";
import Navbar from "./Components/Navbar";

function App() {
  const [height, setHeight] = useState("");
  const [width, setWidth] = useState("");
  return (
    <div className="App">
      <Navbar />
      <DragArea width={width} height={height} />
      <DimensionSetter setHeight={setHeight} setWidth={setWidth} />
      hello world
    </div>
  );
}

export default App;
