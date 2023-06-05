import React from "react";

const Chat = ({ chat }) => {
    return (
        <tr key={chat.id}>
            <td>{chat.title}</td>
            <td>{chat.description}</td>
        </tr>
    );
};

export default Chat;
