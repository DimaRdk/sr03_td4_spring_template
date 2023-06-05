import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";

const Login = (props) => {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')

    const handleLogin = (event) => {
        event.preventDefault();
        console.log("mail = " + mail)
        console.log("password = " + password)
        //TODO Requete http login au backend spring
        // axios.post...
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
                <Link to="/chats">POUR TEST UNIQUEMENT</Link>
            </form>
        </div>
    );
}

export default Login;
