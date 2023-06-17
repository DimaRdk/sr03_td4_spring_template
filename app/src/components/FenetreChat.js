import React from 'react';
import ChatSocket from './ChatSocket';

const FenetreChat = ({chatId, pseudo,title,close}) => {
    console.log('On arrive ici')
    return (
        <div>
            <ChatSocket chatId={chatId} pseudo={pseudo} title={title} close={close} />
        </div>
    );
};

export default FenetreChat;
