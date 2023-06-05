const Chat = ({ chat }) => {
    const handleJoinChat = () => {

    }

    return (
        <tr>
            <td>{chat.title}</td>
            <td>{chat.description}</td>
            <td>
                <button onClick={handleJoinChat}>Rejoindre</button>
            </td>
        </tr>
    )
};

export default Chat;
