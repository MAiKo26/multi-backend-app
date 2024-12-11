import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {BrowserRouter, Routes, Route} from "react-router";
import "./styles/global.css";
import App from "./pages/Home.tsx";
import Explore from "./pages/Explore.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/explore" element={<Explore />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
