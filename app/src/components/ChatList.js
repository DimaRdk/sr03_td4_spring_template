import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import './styles/ChatList.css'
import { useNavigate } from 'react-router-dom';
import NavBar from './NavBar';
import Chat from './Chat';
import UserInfo from "./UserInfo";
import axios from "axios";
let createdChats;
let invitedChats;
const ChatList = () => {
    const [myChats, setMyChats] = useState([]);
    const [invitedChats, setInvitedChats] = useState([]);
    let loggedUserID = localStorage.getItem("userId");
    let loggedUserFirstName = localStorage.getItem("userFirstName");
    let loggedUserLastName = localStorage.getItem("userLastName");
    let loggedUserRole = localStorage.getItem("userRole");
    const navigate = useNavigate();
    const joinChat = async (chatId) => {
        // Logique pour rejoindre un chat avec l'ID spécifié
        console.log(`Rejoindre le chat ${chatId}`);
    };


    const editChat = (chatId) => {
        console.log(`Edit le chat ${chatId}`);
        navigate(`/chatEdition/${chatId}`);
    };

    const deleteChat = async (chatId) => {
        try {
            await axios.delete(`http://localhost:8080/api/chats/?id=`+ chatId);

            const updatedChats = myChats.filter((chat) => chat.id !== chatId);
            setMyChats(updatedChats);
        } catch (error) {
            console.error("Erreur lors de la suppression du chat :", error);
        }
    };
    useEffect(() => {
        async function fetchData() {

            let fetchedCreatedChats;
            let fetchedInvitedChats;

            try {

                const createdChatsResponse = await axios.get("http://localhost:8080/api/createdChat/?id=" + loggedUserID);
                fetchedCreatedChats = createdChatsResponse.data;
            } catch (error) {
                console.error("Error fetching created chats:", error);
            }

            try {
                const invitedChatsResponse = await axios.get("http://localhost:8080/api/invitedChat?id=" + loggedUserID);
                fetchedInvitedChats = invitedChatsResponse.data;
            } catch (error) {
                console.error("Error fetching invited chats:", error);
            }

            console.log(fetchedCreatedChats);
            console.log(fetchedInvitedChats);

            setMyChats(fetchedCreatedChats);
            setInvitedChats(fetchedInvitedChats);
        }

        fetchData();
    }, []);

    return (
        <div>
            <NavBar />
            <div className="content">
                <UserInfo firstname={ loggedUserFirstName} lastname={loggedUserLastName} role={loggedUserRole} />

                <main>
                    <h2>Mes Chats</h2>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Description</th>
                            <th>Date de Création</th>
                            <th>Date d'Expiration</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {myChats &&
                            myChats.map((chat) => (
                                <Chat key={chat.id} chat={chat} joinChat={joinChat} deleteChat={deleteChat} editChat={editChat} />

                            ))}
                        </tbody>
                    </table>
                    <h2>Chats Invités</h2>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Description</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {invitedChats &&
                            invitedChats.map((chat) => (
                                <Chat key={chat.id} chat={chat} joinChat={joinChat} deleteChat={deleteChat} editChat={editChat} />

                            ))}
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
    );
}

export default ChatList;
