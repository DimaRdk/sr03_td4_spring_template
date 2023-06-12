import React, { useState } from 'react';
import {useLocation} from "react-router-dom";
import './styles/PlanifierDiscussion.css'
import NavBar from "./NavBar";
import axios from "axios";
const PlanifierDiscussion = () => {
    let creatorId = localStorage.getItem("userId");
    const [chat, setChat] = useState({
        id: '',
        titre: '',
        description: '',
        creationDate: '',
        expirationDate: '',
    });

    const handleChange = (e) => {
        setChat({
            ...chat,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(chat);
        axios.post("http://localhost:8080/api/chat",{
            'title': chat.titre,
            'description': chat.description,
            'creationDate': chat.creationDate,
            'expirationDate': chat.expirationDate,
            'creator': creatorId
        })
            .then( r => {
                    console.log(r)
                }
            )
            .catch();

    };

    return (
        <div>
             < NavBar />

            <div className="form-container">

                <h2>Planifier une discussion</h2>
                <form onSubmit={handleSubmit}>

                    <div className="form-field">
                        <label>Titre:</label>
                        <input type="text" name="titre" value={chat.titre} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Description:</label>
                        <textarea name="description" value={chat.description} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Date de création:</label>
                        <input type="datetime-local" name="creationDate" value={chat.creationDate} onChange={handleChange} required />
                    </div>
                    <div className="form-field">
                        <label>Date d'expiration:</label>
                        <input type="datetime-local" name="expirationDate" value={chat.expirationDate} onChange={handleChange} required />
                    </div>
                    <button type="submit">Planifier</button>
                </form>
            </div>
        </div>


    );
};

export default PlanifierDiscussion;
