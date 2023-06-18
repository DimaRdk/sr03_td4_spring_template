const Chat = ({ chat, joinChat, deleteChat ,editChat,isInvitedChat}) => {
    const handleJoinChat = () => {
        joinChat(chat.id , chat.title);
    };

    const handleDeleteChat = () => {
        deleteChat(chat.id);
    };

    const handleEditChat = () => {
        editChat(chat.id)
    };

    return (

        <tr>
            <td>{chat.title}</td>
            <td>{chat.description}</td>
            <td>{new Date(chat.creationDate).toLocaleString()}</td>
            <td>{new Date(chat.expirationDate).toLocaleString()}</td>


            {!isInvitedChat &&
                <div className="action-buttons">
                    <button onClick={handleJoinChat}>Rejoindre</button>
                    <button onClick={handleDeleteChat} className="delete-button">Supprimer</button>
                    <button onClick={handleEditChat} className="edit-btn">Modifier</button>
                </div>
            }
            {isInvitedChat &&
                <div className="action-buttons">
                    <button onClick={handleJoinChat}>Rejoindre</button>

                </div>
            }

        </tr>
    );
};

export default Chat;
