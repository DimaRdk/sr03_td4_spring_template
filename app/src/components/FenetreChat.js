import React from 'react';
import ChatSocket from './ChatSocket';

const FenetreChat = ({chatId, pseudo}) => {
    console.log('On arrive ici')
    return (
        <div>
            <ChatSocket chatId={chatId} pseudo={pseudo} />
        </div>
    );
};

export default FenetreChat;
