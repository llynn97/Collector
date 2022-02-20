import React, { useState, useEffect } from "react";
import MainMovieEvents from "./MainMovieEvents";
import { useDispatch } from "react-redux";
import { CINEMA_NAMES } from "../../actions/types";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import {MAIN_EVENTS} from "../../actions/types";
import "../../css/MainPage/MainPage.module.css";


const MainPage = () => {
    const [cinemaNames, setCinemaNames] = useState(["CGV", "롯데시네마", "메가박스", "씨네큐"]);
    const dispatch = useDispatch();

    dispatch({
        type: CINEMA_NAMES,
        cinemaNames: cinemaNames,
    });

    // dispatch({
    //     type: MAIN_EVENTS,
    //     events: data,
    // })



    return(
        <>
        {cinemaNames.map((item, index) => <div key={index}><MainMovieEvents cinemaName={item}/></div>)}

        
        </>
    )

}

export default MainPage;