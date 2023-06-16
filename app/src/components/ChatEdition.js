import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './styles/ChatEdition.css';
import NavBar from "./NavBar";
import axios from "axios";

const ChatEdition = () => {
    const { chatId } = useParams();
    const [chat, setChat] = useState(null);
    const [notification, setNotification] = useState('');
    const now = new Date().toISOString().slice(0,16);
    const [users, setUsers] = useState([]);
    let currentUser = localStorage.getItem("userId");
    useEffect(() => {

        axios.get(`http://localhost:8080/api/chat/?id=` + chatId)
            .then(response => {
                setChat(response.data);
            })
            .catch(error => console.error(`There was an error retrieving the chat: ${error}`));

        axios.get(`http://localhost:8080/api/users/`)
            .then(response => {
                console.log(currentUser)
                setUsers(response.data.filter(user => user.id != currentUser));
            })
            .catch(error => console.error(`There was an error retrieving the active users: ${error}`));
    }, [chatId]);

    const handleInvite = (userId) => {
        axios.post(`http://localhost:8080/api/${chatId}/invite`, { userId: userId })
            .then(response => {
                console.log(response);

            })
            .catch(error => {
                console.error(`There was an error inviting the user: ${error}`);
            });
    };
    const handleChange = (e) => {
        setChat({
            ...chat,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.put(`http://localhost:8080/api/chat/${chatId}`, chat)
            .then(response => {
                console.log(response);
                setNotification('Chat modifié avec succès !');

                setTimeout(() => {
                    setNotification('');
                }, 4000);
            })
            .catch(error => {
                console.error(`There was an error updating the chat: ${error}`);
            });
    };

    return (
        <div>
            < NavBar />
            {chat && (
                <div className="form-container">
                    <h2>Modifier une discussion</h2>
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
                            <input type="datetime-local" name="creationDate" value={chat.creationDate} min={now} onChange={handleChange} disabled={true} required />
                        </div>
                        <div className="form-field">
                            <label>Date d'expiration:</label>
                            <input type="datetime-local" name="expirationDate" value={chat.expirationDate} min={chat.creationDate} onChange={handleChange} required />
                        </div>
                        <button type="submit">Modifier</button>
                    </form>
                    { notification && (
                        <div className={`notification`}>
                            {notification}
                        </div>
                    )}
                </div>
            )}

            {users && (
                <div className="users-container">
                    <h2>Utilisateurs actifs</h2>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Prenom</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map((user) => (
                            <tr key={user.id}>
                                <td>{user.firstName}</td>
                                <td>{user.lastName}</td>
                                <td>
                                    <button onClick={() => handleInvite(user.id)}>Inviter</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}


        </div>
    );
};

export default ChatEdition;
