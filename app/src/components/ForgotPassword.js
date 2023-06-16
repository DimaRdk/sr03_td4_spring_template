import React, { useState } from 'react';
import './styles/ForgotPassword.css'
import axios from "axios";
import {useNavigate} from "react-router-dom";

const ForgotPassword = () => {
    const [showNotification, setShowNotification] = useState(false);
    const [errorNotification, setErrorNotification] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        const email = event.target.email.value;

        axios
            .get(`http://localhost:8080/api/forgotPassword?email=${email}`)
            .then((response) => {
                setShowNotification(true);
                setErrorNotification(false);
                console.log(`Envoie de mail à ${email}`);
                const mdp = response.data;
                console.log(`Le mot de passe est : ${mdp}`);

                setTimeout(() => {
                    setShowNotification(false);
                }, 5000);
            })
            .catch((error) => {
                console.error('Erreur lors de la récupération du mot de passe :', error);
                setShowNotification(true);
                setErrorNotification(true);

                setTimeout(() => {
                    setShowNotification(false);
                    setErrorNotification(false);
                }, 5000);
            });
    };

    const handleGoBack = (event) => {
        event.preventDefault();
        navigate('/');
    };



    return (
        <div className="forgot-password-container">
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">Email</label>
                    <input type="email" name="email" id="email" className="form-control" required />
                </div>
                <button type="submit" className="btn btn-primary w-100">Envoyer le mot de passe</button>
                <button className="btn btn-secondary w-100" onClick={handleGoBack}>
                    Retour
                </button>
            </form>
            {showNotification && (
                <div className={`notification ${errorNotification ? 'error' : ''}`}>
                    {errorNotification ? 'Email non trouvé' : 'L\'email a été envoyé avec succès !'}
                </div>
            )}



        </div>
    );
};

export default ForgotPassword;
