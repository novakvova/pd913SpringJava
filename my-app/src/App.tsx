import React from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from "./components/Home";
import DefaultLayout from "./components/containers/DefaultLayout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DefaultLayout />}>
        <Route index element={<Home />} />
      </Route>
    </Routes>
  );
}

export default App;
