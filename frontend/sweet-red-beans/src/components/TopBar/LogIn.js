import React, {useState} from "react";
import Modal from '../Modals/Modal';
import { useNavigate } from "react-router";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import login from "../../actions/user_action";
import { LOGIN_USER } from "../../actions/types";
import style from "../../css/TopBar/LogIn.module.css";
import { getCookie, setCookie } from "../../Cookie";

const LogIn = () =>{
  let navigation = useNavigate();
  const dispatch = useDispatch();

  const [modalOpen, setModalOpen] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(false);

  const openModal = () => {
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalOpen(false);
  };

  const SigninClick = () => {
    navigation('/signup');
    setModalOpen(false);
  }

  const emailChange = (e) => {
    setEmail(e.target.value);
  }

  const passwordChange = (e) => {
    setPassword(e.target.value)
  }

  const LoginClick = (e) => {
    
    e.preventDefault();
    if (email === "" || password === ""){
      return setLoginError(true);
    }
    else{
      setLoginError(false);
    }

    const body = {
      email: email,
      password: password,
    };

    axios.post('http://localhost:8080/signin', body,{ withCredentials: true })
    .then(response => {
      if(response.data.result){
        dispatch({
          type: LOGIN_USER,
          user: response.data,
        })
        //쿠키에 저장
        const cookie = response.headers.cookies
        
        localStorage.setItem('refresh-token', response.data['refresh-token']);
        setCookie('JSESSIONID',  cookie.load('JSESSIONID') , {path:'/', secure:true, sameSite:"none"})
        
        setModalOpen(false);
        navigation(0);
      } else {
        alert("로그인에 실패했습니다.");
      }
    })
    .catch(error => console.log(error));

  }

  return (
      <>
      <div className={style.login}>
      <button onClick={openModal}>로그인</button>
      </div>

      <Modal open={modalOpen} close={closeModal} header="로그인">
        <form>
        <div>
        <input type="text" placeholder="example@naver.com" onChange={emailChange} value={email}/>
        </div>
        {email === "" ? <div style={{color : 'red'}}>이메일을 입력해주세요</div> : null}

        <div>
        <input type="password" placeholder="********" onChange={passwordChange} value={password}/>
        </div>
        {password === "" ? <div style={{color : 'red'}}>비밀번호를 입력해주세요</div> : null}

        <button onClick={LoginClick}>로그인</button>
        {loginError && <div style={{color : 'red'}}>이메일과 비밀번호를 입력해주세요</div>}
        </form>
        <button id="signup" onClick={SigninClick}>회원가입</button>
        <div>
        <button>카카오로 로그인</button>
        </div>
        <div>
        <button>구글로 로그인</button>
        </div>
      </Modal>
      </>
  );
}

export default LogIn;