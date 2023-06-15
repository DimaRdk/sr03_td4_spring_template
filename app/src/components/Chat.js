const Chat = ({ chat, joinChat, deleteChat ,editChat}) => {
    const handleJoinChat = () => {
        joinChat(chat.id);
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
            <div className="action-buttons">
                <button onClick={handleJoinChat}>Rejoindre</button>
                <button onClick={handleDeleteChat} className="delete-button">Supprimer</button>
                <button onClick={handleEditChat} className="edit-btn">Modifier</button>
            </div>
        </tr>
    );
};

export default Chat;
