import React from 'react';
import ReactDOM from 'react-dom/client';

import App from './App';
import ChatCreation from "./components/ChatCreation";
import reportWebVitals from './reportWebVitals';
import ChatEdition from  "./components/ChatEdition"
import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";
import ChatList from "./components/ChatList";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/chatCreation" element={<ChatCreation />} />
            <Route path="/chatEdition/:chatId" element={<ChatEdition />} />
            <Route path="/" element={<App />} />
            <Route path="/chats" element={<ChatList />} />
        </Routes>
    </BrowserRouter>
);

let loggedUser;
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
