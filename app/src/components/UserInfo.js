// UserInfo.js
import React from 'react';
import './styles/UserInfo.css';
import { useNavigate } from 'react-router-dom';

const UserInfo = ({ firstname, lastname, role }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Mettre à jour la logique de déconnexion si nécessaire
        localStorage.removeItem("userId");
        localStorage.removeItem("userFirstName");
        localStorage.removeItem("userLastName");
        localStorage.removeItem("userRole");

        // Redirigez l'utilisateur vers la page de connexion
        navigate("/");
    };

    return (
        <aside className="user-info">
            <h2>Informations sur l'utilisateur</h2>
            <div className="info-block">
                <span className="info-label">Nom / Prénom :</span>
                <span className="user-name">{lastname} {firstname}</span>
            </div>
            <div className="info-block">
                <span className="info-label">Role :</span>
                <span className="user-role">{role ? "Admin" : "Utilisateur"}</span>
            </div>
            <button onClick={handleLogout} className="logout-button">Se déconnecter</button>
        </aside>
    );
};

export default UserInfo;
