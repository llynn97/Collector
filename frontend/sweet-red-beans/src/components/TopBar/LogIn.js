import React, {useEffect, useState} from "react";
import Modal from '../Modals/Modal';
import { useNavigate } from "react-router";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import login from "../../actions/user_action";
import { LOGIN_USER } from "../../actions/types";
import style from "../../css/TopBar/LogIn.module.css";
import { getCookie, setCookie } from "../../Cookie";
import { Cookies } from "react-cookie";

const LogIn = () =>{
  let navigation = useNavigate();
  const dispatch = useDispatch();

  const [modalOpen, setModalOpen] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(false);

  const [successLogin, setSuccessLogin] = useState(false);
  const KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize?client_id=e64599af67aac20483ad02a14a8c5058&redirect_uri=http://localhost:3000/signin/oauth2/code/kakao&response_type=code"

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

        setSuccessLogin(true);
        setModalOpen(false);
        navigation(0);
        
      } else {
        alert("로그인에 실패했습니다.");
      }
    })
    .catch(error => console.log(error));

  }

  const KakaoLoginClick = (e) => {
    let code = new URL(window.location.href).searchParams.get("code");
    window.open(KAKAO_AUTH_URL);

    
    console.log("으으아아ㅏㅏ")
    console.log(code)
    // axios.get('http://localhost:8080/signin/oauth2/code/kakao',{ 
    //       withCredentials: true,
    //       params: {
    //                 code: code,
    //               }
    //     }).then(response=> {
    //       console.log(response.data)
    //     })

  }

  return (
      <>
      <div className={style.loginArea}>
      {successLogin ? <button>로그아웃</button>: <button onClick={openModal} className={style.loginButton}>로그인</button>}
      
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
        <button href={KAKAO_AUTH_URL} onClick={KakaoLoginClick}>카카오로 로그인</button>
        </div>
        <div>
        <button>구글로 로그인</button>
        </div>
      </Modal>
      </>
  );
}

export default LogIn;