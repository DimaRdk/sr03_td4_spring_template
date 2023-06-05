// UserInfo.js
import React from 'react';
import './styles/UserInfo.css';

const UserInfo = ({ username, role }) => (
    <aside className="user-info">
        <h2>Informations sur l'utilisateur</h2>
        <div className="user-name">{username}</div>
        <div className="user-role">{role}</div>
    </aside>
);

export default UserInfo;
