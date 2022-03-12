import React, { useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios"
import { Cookies } from "react-cookie";
import { Link } from "react-router-dom";

const GoogleLogin = () => {
    let navigation = useNavigate();
    let code = new URL(window.location.href).searchParams.get("code");
    const cookies = new Cookies();

    useEffect(
        async () => {
            const data = await axios.get('http://localhost:8080/signin/auth/google/callback', {
                withCredentials : true,
                params: {
                    code:code,
                }
            });

            const data2 = await axios.post('http://localhost:8080/signin', data.data, { withCredentials: true})

            if(data2.data.result) {
                const date = new Date();
                date.setMinutes(date.getMinutes() + 30);
                cookies.set("login", true);
                navigation(0)
            }
             navigation('/')
    })

    return (
        <>
        </>
    )
}

export default GoogleLogin;