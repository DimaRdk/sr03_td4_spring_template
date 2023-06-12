import React from 'react';
import {NavLink} from "react-router-dom";
import './styles/NavBar.css';

const NavBar = () => {
    return (
        <nav className="navbar">
            <ul className="navbar-nav">
                <li>
                    <NavLink to="/chats" className="nav-link" activeClassName="is-active">Accueil</NavLink>
                </li>
                <li>
                    <NavLink to="/ChatCreation" className="nav-link" activeClassName="is-active">Planifier une discussion</NavLink>
                </li>
            </ul>
        </nav>
    );
};

export default NavBar;
