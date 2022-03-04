import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios"

const KakaoLogin = () => {
    let navigation = useNavigate();
    let code = new URL(window.location.href).searchParams.get("code");

    useEffect(() => {
        console.log(code);
        axios.get('http://localhost:8080/signin/oauth2/code/kakao',{ 
          withCredentials: true,
          params: {
                    code: code,
                  }
        })
        .then(response=> {
            const data = response.data;
            axios.post('http://localhost:8080/signin', data, { withCredentials: true })
            .then(response => {
                if(response.data.result){
                    navigation('/')
          
                } else {
                  alert("로그인에 실패했습니다.");
                }
            })
            .catch(e => console.log(e))
        })
        .catch(e => console.log(e));
    })

    return (
        <>
        </>
    )
}

export default KakaoLogin;