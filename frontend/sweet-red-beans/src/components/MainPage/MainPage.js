import React, { useState, useEffect } from "react";
import MainMovieEvents from "./MainMovieEvents";
import { useDispatch } from "react-redux";
import { CINEMA_NAMES } from "../../actions/types";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import {MAIN_EVENTS} from "../../actions/types";
import style from "../../css/MainPage/MainPage.module.css";
import { Cookies } from "react-cookie";


const MainPage = () => {
    const [cinemaNames, setCinemaNames] = useState(["CGV", "롯데시네마", "메가박스", "씨네큐"]);
    const dispatch = useDispatch();
    const [mainVideo, setMainVideo] = useState("");

    const cookies = new Cookies();

    dispatch({
        type: CINEMA_NAMES,
        cinemaNames: cinemaNames,
    });

    // dispatch({
    //     type: MAIN_EVENTS,
    //     events: data,
    // })

    useEffect(() => {
        axios.get("http://localhost:8080/main/video", {
            withCredentials: true,
        })
        .then(response => {
            setMainVideo(response.data.src)
        })
        .catch(error => console.log(error))

        // const date = new Date();
        // date.setMinutes(date.getMinutes() + 30);

        // cookies.set("login", true, {expires: date});

        // if(sessionStorage.getItem("login")){
        //     const date = new Date();
        //     date.setMinutes(date.getMinutes() + 30);
        //     cookies.set("login", true, {expires: date});
        //     //sessionStorage.removeItem("login");
        // }
        
    }, [])


    return(
        <>
        <iframe src={mainVideo} style={{width:"1000px", height:"300px"}}></iframe>
        {cinemaNames.map((item, index) => <div key={index} className={style.movieThumbnail}><MainMovieEvents cinemaName={item}/></div>)}

        
        </>
    )

}

export default MainPage;