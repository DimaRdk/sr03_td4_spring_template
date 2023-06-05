import React from 'react';
import {Link} from "react-router-dom";
import './styles/NavBar.css';

const NavBar = () => {
    return (
        <nav className="navbar">
            <ul className="navbar-nav">
                <li>
                    <Link to="/chats" className="nav-link">Accueil</Link>
                </li>
                <li>
                    <Link to="/ChatCreation" className="nav-link">Planifier une discussion</Link>
                </li>
                <li>
                    <Link to="/chats" className="nav-link">Mes salons de discussion</Link>
                </li>

            </ul>
        </nav>
    );
};

export default NavBar;
