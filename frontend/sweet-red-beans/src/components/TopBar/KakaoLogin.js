import React, { useEffect } from "react";

const KakaoLogin = () => {
    let code = new URL(window.location.href).searchParams.get("code");

    useEffect(() => {
        console.log(code);
    })

    return (
        <>
        </>
    )
}

export default KakaoLogin;