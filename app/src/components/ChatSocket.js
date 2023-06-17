import React, { useEffect, useRef, useState } from 'react';

const ChatSocket = ({ chatId, pseudo }) => {
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState('');
    const websocket = useRef(null);

    useEffect(() => {

        websocket.current = new WebSocket(`ws://localhost:8080/chatserver/${chatId}/${pseudo}`);

        websocket.current.onopen = () => {
            console.log('Connecté au serveur WebSocket');
        };

        websocket.current.onmessage = (event) => {
            setMessages((messages) => [...messages, event.data]);
        };

        websocket.current.onclose = () => {
            console.log('Déconnecté du serveur WebSocket');
        };


        return () => {
            websocket.current.close();
        };
    }, [chatId, pseudo]);

    const handleSendMessage = () => {
        if (websocket.current && websocket.current.readyState === WebSocket.OPEN) {
            websocket.current.send(message);
            setMessage('');
        }
    };

    return (
        <div>
            <h1>Chat</h1>
            <ul>
                {messages.map((message, i) => (
                    <li key={i}>{message}</li>
                ))}
            </ul>
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
            />
            <button onClick={handleSendMessage}>Envoyer</button>
        </div>
    );
};

export default ChatSocket;
