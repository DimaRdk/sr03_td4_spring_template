import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import './styles/ChatList.css'

import NavBar from './NavBar';
import Chat from './Chat';
import UserInfo from "./UserInfo";
import axios from "axios";

const ChatList = () => {
    const [myChats, setMyChats] = useState([]);
    const [invitedChats, setInvitedChats] = useState([]);
    let loggedUser = JSON.parse(localStorage.getItem("user"));
    console.log(loggedUser);

    useEffect( () => {
        let createdChats
        let invitedChats
        async function fetchData(){
            createdChats = await axios.get("http://localhost:8080/api/createdChat/?id="+loggedUser.id)
                .then(r => {
                    return r.data;
                })
                .catch();

            invitedChats = await axios.get("http://localhost:8080/api/invitedChat?id=" + loggedUser.id)
                .then(r => {
                    return r.data;
                })
                .catch();

            setMyChats(createdChats);
            setInvitedChats(invitedChats);
        }


        setMyChats(createdChats);
        setInvitedChats(invitedChats);
    }, [])

    return (
        <div>
            <NavBar loggedUser={loggedUser}/>
            <div className="content">
                <UserInfo firstname={loggedUser && loggedUser.firstName} lastname={loggedUser && loggedUser.lastName} role={loggedUser && loggedUser.admin} />

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
