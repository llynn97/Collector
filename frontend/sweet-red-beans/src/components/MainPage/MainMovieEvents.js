import React, { useEffect, useState } from "react";
import MovieThumbnail from "./MovieThumbnail";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import { useDispatch, useSelector } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import style from "../../css/MainPage/MainMovieEvents.module.css"
import Slider from "react-slick";
import "../../css/slick/slick.css";
import "../../css/slick/slick-theme.css";

const MainMovieEvents = ({cinemaName}) => {
    const dispatch = useDispatch();
    const [events, setEvents] = useState([]);
    const [thisEvents, setThisEvents] = useState([]);
    const filterMovieList = [];

    useEffect(()=>
    {
        axios.post('http://localhost:8080/main/event-limit', {} ,{ withCredentials: true })
        .then(response => {
            setEvents(response.data);
        });
    } 
    , []);

    useEffect(()=>{
        events.map((item, index) => {
            if(item.cinema_name === cinemaName){
                filterMovieList.push(item)
            }
        })
        setThisEvents(filterMovieList);
        dispatch({
            type: MAIN_CINEMA_EVENTS,
            mainCinemaEvents: {cinemaName:cinemaName, mainCinemaEvents: filterMovieList},
        })
    }, [events])

    const settings = { 
        dots: false,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 3
    };
    
    const test = ["이벤트1", "이벤트2", "이벤트3", "이벤트4", "이벤트5", "이벤트6"]

    return (
        <>
        <div className={style.cinemaName}>{cinemaName}</div>
        <div className={style.movieThumbnails}>
        
        <Slider {...settings}> 
        {
        //thisEvents!==[] ? thisEvents.map((item, index) => <div key={index}><MovieThumbnail thisEvent={item}/></div>) : null
        }
        {
        test.map((item, index) => <div>{item}</div>)
        }
        </Slider>
        </div>

        
        </>
        
    );
}

export default MainMovieEvents;