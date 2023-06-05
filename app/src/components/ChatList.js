import React, {useEffect, useState} from "react";
import './styles/ChatList.css'

import NavBar from './NavBar';
import Chat from './Chat';
import UserInfo from "./UserInfo";

const ChatList = (props) => {
    const [myChats, setMyChats] = useState([]);
    const [invitedChats, setInvitedChats] = useState([]);

    useEffect(() => {
        // Remplacer ces deux appels par des appels à votre backend pour obtenir les chats
        setMyChats([
            {
                'id': 1,
                'title': 'My chat 1',
                'description' : 'le 1er chat'
            },
            {
                'id': 2,
                'title': 'My chat 2',
                'description' : 'le 2e chat'
            }
        ]);

        setInvitedChats([
            {
                'id': 3,
                'title': 'Invited chat 1',
                'description' : 'le 3e chat'
            },
            {
                'id': 4,
                'title': 'Invited chat 2',
                'description' : 'le 4e chat'
            }
        ]);
    }, [])

    return (
        <div>
            <NavBar />
            <div className="content">
                <UserInfo username="Bob" role="Admin" />
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
