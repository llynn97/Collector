import React, { useEffect, useState } from "react";
import MovieThumbnail from "./MovieThumbnail";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import { useDispatch, useSelector } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import style from "../../css/MainPage/MainMovieEvents.module.css"
import Slider from "react-slick";
// import "slick-carousel/slick/slick.css";
// import "slick-carousel/slick/slick-theme.css";
import "../../css/slick/slick.css"
import "../../css/slick/slick-theme.css"
import Test from "./Test";


const MainMovieEvents = ({cinemaName}) => {
    const dispatch = useDispatch();
    const [events, setEvents] = useState([]);
    const [thisEvents, setThisEvents] = useState([]);
    const filterMovieList = [];

    useEffect(()=>
    {
        
        axios.get('http://localhost:8080/main/event-limit', {withCredentials: true})
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

    useEffect(() => {
    }, [thisEvents])

    const settings = { 
        dots: false,
        arrows:true,
        infinite: true,
        speed: 500,
        slidesToShow: 2,
        slidesToScroll: 2,
    };
    return (
        <>
        <div className={style.cinemaName}>
            {cinemaName}
            <div className={style.underline}></div>
        </div>
        <div className={style.movieThumbnails}>
        
        {
            thisEvents!==[] ? 
            <Slider {...settings}> 

            {
                thisEvents.map((item, index) => <div key={index}><MovieThumbnail thisEvent={item}/></div>)
            //thisEvents!==[] ? thisEvents.map((item, index) => <div key={index}><MovieThumbnail thisEvent={item}/></div>) : null
            //test.map((item, index) => <div>{item}</div>)
            }
        
            </Slider>
        : null
        }
        
        </div>

        
        </>
        
    );
}

export default MainMovieEvents;