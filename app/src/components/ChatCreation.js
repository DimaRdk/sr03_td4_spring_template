import React, { useState } from 'react';
import {useLocation} from "react-router-dom";
import './styles/PlanifierDiscussion.css'
import NavBar from "./NavBar";
import axios from "axios";
const PlanifierDiscussion = () => {
    let creatorId = localStorage.getItem("userId");
    const [notification, setNotification] = useState('');
    const now = new Date().toISOString().slice(0,16);

    const initialChatState = {
        id: '',
        titre: '',
        description: '',
        creationDate: '',
        expirationDate: '',
    };

    const [chat, setChat] = useState(initialChatState);



    const handleChange = (e) => {
        setChat({
            ...chat,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(chat.title)

        axios.post(`http://localhost:8080/api/chat?creatorId=${creatorId}&title=${chat.title}&description=${chat.description}&creationDate=${chat.creationDate}&expirationDate=${chat.expirationDate}`)

            .then(r => {
                console.log(r);
                setNotification('Chat créé avec succès !');
                setChat(initialChatState);

                setTimeout(() => {
                    setNotification('');
                }, 4000);
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
            });

    };

    return (
        <div>
             < NavBar />

            <div className="form-container">

                <h2>Planifier une discussion</h2>
                <form onSubmit={handleSubmit}>

                    <div className="form-field">
                        <label>Titre:</label>
                        <input type="text" name="title" value={chat.title} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Description:</label>
                        <textarea name="description" value={chat.description} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Date de création:</label>
                        <input type="datetime-local" name="creationDate" value={chat.creationDate} min={now} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Date d'expiration:</label>
                        <input type="datetime-local" name="expirationDate" value={chat.expirationDate} min={chat.creationDate} onChange={handleChange} disabled={!chat.creationDate} required />
                    </div>
                    <button type="submit">Planifier</button>
                </form>

                { notification && (
                    <div className={`notification`}>
                        {notification}
                    </div>
                )}
            </div>
        </div>


    );
};

export default PlanifierDiscussion;
