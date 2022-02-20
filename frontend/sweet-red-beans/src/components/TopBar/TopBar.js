import React, {Fragment} from "react";
import LogIn from "./LogIn";
import NavigationBar from "./NavigationBar";
import {useNavigate} from "react-router-dom";
import style from "../../css/TopBar/TopBar.module.css"

const TopBar = () => {
    let navigation = useNavigate();

    const titleClick = () => {
        navigation('/');
    }

    return (
        <Fragment>
        <div>
        <LogIn/>
        </div>

        
        <div className={style.container}>
            <div className={style.title}>
                <button onClick={titleClick}>타이틀</button>
            </div>
            <div className={style.navigationBar}>
                <NavigationBar/>
            </div>
        </div>

        </Fragment>
    );
    
}

export default TopBar;