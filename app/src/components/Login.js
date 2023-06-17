import {useState} from "react";
import {Link, useNavigate} from 'react-router-dom';
import axios from "axios";
import './styles/Login.css'
import Banierre from "./Banierre";

const Login = (props) => {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState(null);
    const [loginAttempts, setLoginAttempts] = useState(0);
    const navigate = useNavigate();
    let attemptedUser;

    const handleLogin = async (event) => {
        event.preventDefault();
        setErrorMessage(null);  // Reset the error message on every login attempt

        if (loginAttempts >= 5) {
            setErrorMessage('Trop de tentatives de connexion. Veuillez réessayer plus tard.');
            return;
        }

        axios.get("http://localhost:8080/api/login", {
            params: {
                mail: mail,
                password: password
            },
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then((res) => {
                attemptedUser = res.data;

                localStorage.setItem("userId", attemptedUser.id);
                localStorage.setItem("userFirstName", attemptedUser.firstName);
                localStorage.setItem("userLastName", attemptedUser.lastName);
                localStorage.setItem("userRole", attemptedUser.admin);
                setLoginAttempts(0);  // Reset the attempts count on successful login
                navigate("/chats");
            })
            .catch((error) => {
                if (error.response && error.response.status === 404) {
                    setErrorMessage('Email ou mot de passe incorrect');
                    setLoginAttempts(prevAttempts => prevAttempts + 1);  // Increase the attempts count on failed login
                } else {
                    setErrorMessage('Une erreur est survenue. Veuillez réessayer.');
                }
            });
    }

    return (
        <div className="login-container">
            <div className="login-banner">
                <h1>Ut'Chat</h1>
            </div>
            <form onSubmit={handleLogin}>
                <div className="mb-3">
                    <label htmlFor="mail" className="form-label">Email</label>
                    <input type="email" name="mail" className="form-control" id="mail" value={mail}
                           onChange={e => {
                               setMail(e.target.value)
                           }} required={true}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Password</label>
                    <input type="password" name="password" className="form-control" id="password" value={password}
                           onChange={e => {
                               setPassword(e.target.value)
                           }} required={true}/>
                </div>
                {errorMessage && <div className="alert alert-danger custom-alert" role="alert">{errorMessage}</div>}
                <button type="submit" className="btn btn-primary w-100">Connexion</button>

                <Link to="/forgotPassword" className="btn btn-link">Mot de passe oublié ?</Link>
            </form>
        </div>
    );
}

export default Login;
