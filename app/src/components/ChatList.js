import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import './styles/ChatList.css'

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
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {myChats && myChats.map((chat) => (
                            <Chat chat={chat}/>
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
                        {invitedChats && invitedChats.map((chat) => (
                            <Chat chat={chat}/>
                        ))}
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
    );
}

export default ChatList;
