import React from "react";
import LogIn from "./LogIn";
import NavigationBar from "./NavigationBar";
import {useNavigate} from "react-router-dom";

const TopBar = () => {
    let navigation = useNavigate();

    const titleClick = () => {
        navigation('/');
    }

    return (
        <>
        <LogIn/>
        <div>
        <button onClick={titleClick}>타이틀</button>
        </div>
        <NavigationBar/>
        </>
    );
    
}

export default TopBar;