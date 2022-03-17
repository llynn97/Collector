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
import { Link } from "react-router-dom";

const LogIn = () =>{
  let navigation = useNavigate();
  const dispatch = useDispatch();

  const [modalOpen, setModalOpen] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(false);

  const KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize?client_id=e64599af67aac20483ad02a14a8c5058&redirect_uri=http://localhost:3000/signin/oauth2/code/kakao&response_type=code"
  const GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth?scope=email profile&response_type=code&client_id=435089655733-6v1fo661d0dda2ue3ql61420dtquril1.apps.googleusercontent.com&redirect_uri=http://localhost:3000/signin/auth/google/callback"

  const cookies = new Cookies();

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
      method: "일반",
    };

    axios.post('http://localhost:8080/signin', body, { withCredentials: true })
    .then(response => {
      if(!response.data.status) {
        alert("정지된 상태입니다.");
        setModalOpen(false);
      }
      else if(response.data.result){
        // dispatch({
        //   type: LOGIN_USER,
        //   user: response.data,
        // })

        const date = new Date();
        date.setMinutes(date.getMinutes() + 30);
        cookies.set("login", true, {expires: date});
        cookies.set("user", {
          authority:response.data.authority,
          porfileImage:response.data.image_url,
          nickname:response.data.nickname,
        }, {expires:date});

        setModalOpen(false);
        navigation(0)

        
      } else {
        alert("로그인에 실패했습니다.");
      }
    })
    .catch(error => console.log(error));

  }

  const KakaoLoginClick = (e) => {
    window.open(KAKAO_AUTH_URL, "_self");
  }

  const logoutClick = () => {
    axios.post('http://localhost:8080/users/logout', {}, { withCredentials: true })
    .then(response => {
      if(response.data.result){
        cookies.remove("login")
        cookies.remove("user")
        navigation(0)
      } else {
        alert("로그아웃에 실패했습니다.");
      }
    })
    .catch(error => console.log(error));
  }

  const googleLoginClick = () => {
    window.open(GOOGLE_AUTH_URL, "_self");
  }

  return (
      <>
      <div className={style.loginArea}>
      {cookies.get('login') ? <button onClick={logoutClick} className={style.loginButton}>로그아웃</button> 
      : <button onClick={openModal} className={style.loginButton}>로그인</button>}
      </div>

      <Modal open={modalOpen} close={closeModal} header="로그인">
        <form>
          <div className={style.emailArea}>
          <input type="text" placeholder="example@naver.com" onChange={emailChange} value={email} className={style.inputText}/>
          {email === "" ? <div className={style.errormessage}>*이메일을 입력해주세요</div> : null}
          </div>

          <div className={style.passwordArea}>
          <input type="password" placeholder="********" onChange={passwordChange} value={password} className={style.inputText}/>
          {password === "" ? <div className={style.errormessage}>*비밀번호를 입력해주세요</div> : null}
          </div>
          
          <button onClick={LoginClick} className={style.innerloginButton}>로그인</button>
          {loginError && <div className={style.errormessage}>*이메일과 비밀번호를 입력해주세요</div>}
        </form>

        <div id="signup" onClick={SigninClick} className={style.signupButton}>회원가입</div>
        <div>
        <button onClick={KakaoLoginClick} className={style.kakaologinButton}>카카오로 로그인</button>
        </div>
        <div>
        <button onClick={googleLoginClick} className={style.googleloginButton}>구글로 로그인</button>
        </div>
      </Modal>
      </>
  );
}

export default LogIn;