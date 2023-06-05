import React, { useState } from 'react';
import './styles/PlanifierDiscussion.css'
import NavBar from "./NavBar";
const PlanifierDiscussion = () => {
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
