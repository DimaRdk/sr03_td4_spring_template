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
        axios.get("http://localhost:8080/api/login", {
            params: {
                mail: mail,
                password: password
            },
            headers: {
                'Content-Type': 'application/json'
            }
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
            .catch((error) => {
                if (error.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    // The request was made but no response was received
                    console.log(error.request);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error.config);
            });
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