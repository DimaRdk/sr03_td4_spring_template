import {useState} from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import './styles/Login.css'


const Login = (props) => {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate();
    let attemptedUser;

    const handleLogin = async (event) => {
        console.log('laala')
        localStorage.removeItem("userId");
        localStorage.removeItem("userFirstName");
        localStorage.removeItem("userLastName");
        localStorage.removeItem("userRole");
        event.preventDefault();
        axios.post("http://localhost:8080/api/login", {
                'mail' : mail,
                'password' : password
            })
            .then(

                (res) => {
                    console.log('on est la ')
                    console.log(res);
                    attemptedUser = res.data;

                    localStorage.setItem("userId", attemptedUser.id);
                    localStorage.setItem("userFirstName", attemptedUser.firstName);
                    localStorage.setItem("userLastName", attemptedUser.lastName);
                    localStorage.setItem("userRole", attemptedUser.admin);
                    navigate("/chats");
                })
            .catch();
        // TODO faire le mécanisme lorsque mauvaise connexion
    }

    return (
        <div className="login-container">
            <form>
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
                    <div className="invalid-feedback">Login ou mot de passe incorrect</div>
                </div>
                <button type="submit" className="btn btn-primary w-100" onClick={handleLogin}>Connexion</button>
            </form>
        </div>
    );
}

export default Login;
