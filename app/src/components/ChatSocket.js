import React, { useEffect, useRef, useState } from 'react';
import { Box, Button, Input, List, ListItem, TextField, Typography, Paper } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const ChatSocket = ({ chatId, pseudo ,title,close}) => {
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
        <Box sx={{ position: 'relative', display: 'flex', flexDirection: 'column', height: '100%' }}>
            <Box sx={{ position: 'absolute', top: 0, right: 0, p: 1 }}>
                <Button variant="text" color="primary" onClick={close}>
                    <CloseIcon />
                </Button>
            </Box>
            <Typography variant="h4" component="div" align="center" sx={{ pt: 2, pb: 2 }}>{title}</Typography>
            <Paper variant="outlined" sx={{ flexGrow: 1, overflow: 'auto', p: 2, mb: 2 }}>
                <List>
                    {messages.map((message, i) => (
                        <ListItem key={i}>
                            <Typography>{message}</Typography>
                        </ListItem>
                    ))}
                </List>
            </Paper>
            <Box sx={{ position: 'bot', width: '100%', display: 'flex', alignItems: 'center', p: 1 }}>
                <TextField
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    sx={{ flexGrow: 1, mr: 1 }}
                    variant="outlined"
                    placeholder="Entrez votre message..."
                />
                <Button variant="contained" color="primary" onClick={handleSendMessage}>Envoyer</Button>
            </Box>
        </Box>
    );
};

export default ChatSocket;
