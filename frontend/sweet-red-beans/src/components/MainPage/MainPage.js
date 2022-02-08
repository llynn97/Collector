import React, { useState, useEffect } from "react";
import MainMovieEvents from "./MainMovieEvents";
import { useDispatch } from "react-redux";
import { CINEMA_NAMES } from "../../actions/types";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import {MAIN_EVENTS} from "../../actions/types";

const MainPage = () => {
    //const [cinemaNames, setCinemaNames] = useState(["CGV", "롯데시네마", "메가박스", "씨네큐"]);
    const [cinemaNames, setCinemaNames] = useState(["CGV", "롯데시네마", "메가박스", "씨네큐"]);
    const dispatch = useDispatch();

    const data = [
        {
            cinema_name: "CGV",
            event_id: 1,
            thumbnail_url: "이미지url",
            title: "이벤트1",
            start_date: "2022-02-02",
            end_date: "2022-03-03",
        },
        {
            cinema_name: "롯데시네마",
            event_id: 2,
            thumbnail_url: "이미지url",
            title: "이벤트2",
            start_date: "2022-02-02",
            end_date: "2022-03-03",
        },
        {
            cinema_name: "CGV",
            event_id: 3,
            thumbnail_url: "이미지url",
            title: "이벤트3",
            start_date: "2022-02-02",
            end_date: "2022-03-03",
        },
        {
            cinema_name: "메가박스",
            event_id:4,
            thumbnail_url: "이미지url",
            title: "이벤트4",
            start_date: "2022-02-02",
            end_date: "2022-03-03",
        },
        {
            cinema_name: "CGV",
            event_id: 5,
            thumbnail_url: "이미지url",
            title: "이벤트5",
            start_date: "2022-02-02",
            end_date: "2022-03-03",
        }
    ]

    dispatch({
        type: CINEMA_NAMES,
        cinemaNames: cinemaNames,
    });

    dispatch({
        type: MAIN_EVENTS,
        events: data,
    })

    // dispatch(mainEvents())
    // .then(response=> {})
    // .catch(error => console.log(error));

    return(
        <>
        <div>main page</div>
        {cinemaNames.map((item, index) => <div key={index}><MainMovieEvents cinemaName={item}/></div>)}
        </>
    )

}

export default MainPage;