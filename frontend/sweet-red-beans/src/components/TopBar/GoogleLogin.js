import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios"
import { Cookies } from "react-cookie";
import { Link } from "react-router-dom";

const GoogleLogin = () => {
    let navigation = useNavigate();
    let code = new URL(window.location.href).searchParams.get("code");
    const cookies = new Cookies();

    useEffect(() => {
        console.log(code);
        axios.get('http://localhost:8080/signin/auth/google/callback',{ 
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
                    const date = new Date();
                    date.setMinutes(date.getMinutes() + 30);
                    cookies.set("login", true, {expires: date});
                
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

export default GoogleLogin;