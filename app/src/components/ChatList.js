import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import './styles/ChatList.css'
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Modal from 'react-modal'
import NavBar from './NavBar'
import Banierre from "./Banierre";
import Chat from './Chat';
import UserInfo from "./UserInfo";
import axios from "axios";
import ChatSocket from "./ChatSocket";
import FenetreChat from "./FenetreChat";
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
    const [selectedChatId, setSelectedChatId] = useState(null);
    const [selectedPseudo, setSelectedPseudo] = useState(null);
    const [selectedTitle, setSelectedTitle] = useState(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const joinChat = (chatId,chatTitle) => {
        setSelectedTitle(chatTitle);
        setSelectedChatId(chatId);
        setSelectedPseudo(loggedUserFirstName+' '+loggedUserLastName);
        setModalIsOpen(true);
    };

    const closeModal = () => {
        setModalIsOpen(false);
        setSelectedChatId(null);
        setSelectedPseudo(null);
    };


    const editChat = (chatId, ) => {
        console.log(`Edit le chat ${chatId}`);
        navigate(`/chatEdition/${chatId}`);
    };

    const deleteChat = async (chatId) => {
        try {
            await axios.post(`http://localhost:8080/api/Suprchat?id=`+ chatId);

            const updatedChats = myChats.filter((chat) => chat.id !== chatId);
            setMyChats(updatedChats);
        } catch (error) {
            console.error("Erreur lors de la suppression du chat :", error);
        }
    };
    useEffect(() => {

            if (!loggedUserID) {
                navigate('/');
                    }


        async function fetchData() {
            let fetchedCreatedChats;
            let fetchedInvitedChats;

            try {
                const createdChatsResponse = await axios.get("http://localhost:8080/api/createdChat/?id=" + loggedUserID);
                fetchedCreatedChats = createdChatsResponse.data;

                fetchedCreatedChats.forEach(async (chat) => {
                    if (new Date(chat.expirationDate) < new Date()) {
                        await deleteChat(chat.id);
                    }
                });
            } catch (error) {
                console.error("Error fetching created chats:", error);
            }

            try {
                const invitedChatsResponse = await axios.get("http://localhost:8080/api/invitedChat?id=" + loggedUserID);
                fetchedInvitedChats = invitedChatsResponse.data;

                fetchedInvitedChats.forEach(async (chat) => {
                    if (new Date(chat.expirationDate) < new Date()) {
                        await deleteChat(chat.id);
                    }
                });
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
            <Banierre />
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
                                <Chat key={chat.id} chat={chat} joinChat={joinChat} deleteChat={deleteChat} editChat={editChat} isInvitedChat={false } />

                            ))}
                        </tbody>
                    </table>
                    <h2>Chats Invités</h2>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Description</th>
                            <th>Date de Création</th>
                            <th>Date d'Expiration</th>
                            <th className="action-title">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {invitedChats &&
                            invitedChats.map((chat) => (
                                <Chat key={chat.id} chat={chat} joinChat={joinChat} deleteChat={deleteChat} editChat={editChat} isInvitedChat={true} />
                            ))}
                        </tbody>
                    </table>
                    <Modal
                        isOpen={modalIsOpen}
                        onRequestClose={closeModal}
                        contentLabel="Fenêtre de chat"
                        style={{
                            overlay: { backgroundColor: 'rgba(0, 0, 0, 0.8)' },
                            content: {
                                width: '60%',
                                height: '60%',
                                top: '25%',
                                left: '25%',
                                transform: 'translate(-25%, -25%)',
                                color: 'lightsteelblue'
                            }
                        }}
                    >

                        {selectedChatId && selectedPseudo  &&(
                            <FenetreChat chatId={selectedChatId} pseudo={selectedPseudo} title={selectedTitle} close={closeModal}/>
                        )}

                    </Modal>
                </main>
            </div>
        </div>
    );
}

export default ChatList;
