import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './styles/ChatEdition.css';
import NavBar from "./NavBar";
import axios from "axios";
import Banierre from "./Banierre";

import { useNavigate } from 'react-router-dom';



const ChatEdition = () => {
    const { chatId } = useParams();
    const [chat, setChat] = useState(null);
    const [notification, setNotification] = useState('');
    const now = new Date().toISOString().slice(0,16);
    const [users, setUsers] = useState([]);
    const [chatUsers, setChatUsers] = useState([]);
    let currentUser = localStorage.getItem("userId");
    const navigate = useNavigate();


    useEffect(() => {
        if (!currentUser) {
            navigate('/');
        }

        axios.get(`http://localhost:8080/api/chat/${chatId}/members`)
            .then(response => {
                setChatUsers(response.data);
                return response.data;
            })
            .then(chatMembers => {
                axios.get(`http://localhost:8080/api/users/`)
                    .then(response => {
                        setUsers(response.data.filter(user => user.id != currentUser && !chatMembers.map(chatUser => chatUser.id).includes(user.id)));
                    })
                    .catch(error => console.error(`There was an error retrieving the active users: ${error}`));
            })
            .catch(error => console.error(`There was an error retrieving the chat members: ${error}`));

        axios.get(`http://localhost:8080/api/chat/?id=` + chatId)
            .then(response => {
                setChat(response.data);
            })
            .catch(error => console.error(`There was an error retrieving the chat: ${error}`));
    }, [chatId]);

    const handleInvite = (userId) => {
        axios.post(`http://localhost:8080/api/${chatId}/invite`, { userId: userId })
            .then(response => {
                console.log(response);
                // Refetch chat members
                axios.get(`http://localhost:8080/api/chat/${chatId}/members`)
                    .then(response => {
                        setChatUsers(response.data);
                        return response.data;
                    })
                    .then(chatMembers => {

                        axios.get(`http://localhost:8080/api/users/`)
                            .then(response => {
                                setUsers(response.data.filter(user => user.id != currentUser && !chatMembers.map(chatUser => chatUser.id).includes(user.id)));
                            })
                            .catch(error => console.error(`There was an error retrieving the active users: ${error}`));
                    })
                    .catch(error => console.error(`There was an error retrieving the chat members: ${error}`));
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

    const handleSupprimer = (userId) => {
        axios.delete(`http://localhost:8080/api/chat/${chatId}/members/${userId}`)
            .then(response => {
                console.log(response);
                // Refetch chat members
                axios.get(`http://localhost:8080/api/chat/${chatId}/members`)
                    .then(response => {
                        setChatUsers(response.data);
                        return response.data;
                    })
                    .then(chatMembers => {

                        axios.get(`http://localhost:8080/api/users/`)
                            .then(response => {
                                setUsers(response.data.filter(user => user.id != currentUser && !chatMembers.map(chatUser => chatUser.id).includes(user.id)));
                            })
                            .catch(error => console.error(`There was an error retrieving the active users: ${error}`));
                    })
                    .catch(error => console.error(`There was an error retrieving the chat members: ${error}`));
            })
            .catch(error => {
                console.error(`There was an error removing the user from the chat: ${error}`);
            });
    };

    return (
        <div>
            <Banierre />
            <NavBar />
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

            {users && chatUsers && (
                <div className="tables-container">
                    <div className="users-container">
                        <h2>Inviter des utilisateurs</h2>
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
                    <div className="chat-users-container">
                        <h2>Membres du Chat</h2>
                        <table className="table">
                            <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Prenom</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {chatUsers.map((user) => (
                                <tr key={user.id}>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>
                                        <button className="button-delete" onClick={() => handleSupprimer(user.id)}>Supprimer</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}

        </div>
    );
};

export default ChatEdition;
