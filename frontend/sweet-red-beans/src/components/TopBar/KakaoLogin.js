import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios"
import { Cookies } from "react-cookie";

const KakaoLogin = () => {
    let navigation = useNavigate();
    let code = new URL(window.location.href).searchParams.get("code");
    const cookies = new Cookies();

    useEffect(() => {
        console.log(code);
        axios.get('http://localhost:8080/signin/oauth2/code/kakao',{ 
          withCredentials: true,
          params: {
                    code: code,
                  }
        })
        .then(response=> {
            console.log(response.data);
            const data = response.data;
            axios.post('http://localhost:8080/signin', data, { withCredentials: true })
            .then(response => {
                if(response.data.result){
                    console.log("???");
                    console.log(cookies);
                    sessionStorage.setItem("login", true);
                    navigation('/')
          
                } else {
                  alert("로그인에 실패했습니다.");
                }
            })
            .catch(e => console.log(e))
        })
        .catch(e => console.log(e));

        return () => {

            // const date = new Date();
            // date.setMinutes(date.getMinutes() + 30);
            //cookies.set("login", true);
        }
    }, [])

    return (
        <>
        </>
    )
}

export default KakaoLogin;