import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";

const ChatList = (props) => {
    const [chats, setChats] = useState([])

    // => "onload"
    useEffect(() => {
        //TODO Recuperer la liste des chats du user depuis le backend spring
        // axios.get...
        setChats([
            {
                'id': 1,
                'title': 'chat 1',
                'description' : 'le 1er chat'
            },
            {
                'id': 2,
                'title': 'chat 2',
                'description' : 'le 2e chat'
            }
        ])
    }, [])

    return (
        <div>
            <nav className="navbar navbar-expand-lg">
                <div className="container-fluid">
                    <Link className={'navbar-brand'} to="/chats">Accueil</Link>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <Link className={'nav-link'} to="/chats">Planifier une discussion</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={'nav-link'} to="/chats">Mes salons de discussion</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={'nav-link'} to="/chats">Mes invitations</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div className="content">
                <aside>
                    <div>Bob</div>
                    <div>Admin</div>
                </aside>
                <main>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {chats && chats.map((chat) => (
                            <tr key={chat.id}>
                                <td>{chat.title}</td>
                                <td>{chat.description}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
    );
}

export default ChatList;
