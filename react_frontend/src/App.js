import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import GamePage from "./components/GamePage";
import PlayerRecords from "./components/PlayerRecords";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route exact path="/" element={<HomePage />} />
        <Route path="/game" element={<GamePage />} />
        <Route path="/history" element={<PlayerRecords />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
