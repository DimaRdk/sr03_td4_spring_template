// UserInfo.js
import React from 'react';
import './styles/UserInfo.css';

const UserInfo = ({ firstname, lastname, role }) => (
    <aside className="user-info">
        <h2>Informations sur l'utilisateur</h2>
        <div className="user-name">{lastname} {firstname}</div>
        <div className="user-role">{role ? "Admin" : "Utilisateur"}</div>
    </aside>
);

export default UserInfo;
