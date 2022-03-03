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
        slidesToScroll: 1,
    };
    
    

    const test2 = [{
        cinema_name: "씨네큐",
        end_date: "2022-03-06",
        event_id: 10,
        is_like: false,
        start_date: "2022-02-21",
        thumbnail_url: "https://file.cineq.co.kr/j.aspx?guid=0c6274725b8941188c865226523f5d3f",
        title: "<극장판 주술회전 0> 캘린더보드 증정 이벤트"
        
    }, {
        cinema_name: "씨네큐",
        end_date: "2022-03-06",
        event_id: 10,
        is_like: false,
        start_date: "2022-02-21",
        thumbnail_url: "https://file.cineq.co.kr/j.aspx?guid=0c6274725b8941188c865226523f5d3f",
        title: "<극장판 주술회전 0> 캘린더보드 증정 이벤트"
        
    }, {
        cinema_name: "씨네큐",
        end_date: "2022-03-06",
        event_id: 10,
        is_like: false,
        start_date: "2022-02-21",
        thumbnail_url: "https://file.cineq.co.kr/j.aspx?guid=0c6274725b8941188c865226523f5d3f",
        title: "<극장판 주술회전 0> 캘린더보드 증정 이벤트"
        
    }, {
        cinema_name: "씨네큐",
        end_date: "2022-03-06",
        event_id: 10,
        is_like: false,
        start_date: "2022-02-21",
        thumbnail_url: "https://file.cineq.co.kr/j.aspx?guid=0c6274725b8941188c865226523f5d3f",
        title: "<극장판 주술회전 0> 캘린더보드 증정 이벤트"
        
    }]
    const test = [<MovieThumbnail thisEvent={test2}/>, <MovieThumbnail thisEvent={test2}/>,<MovieThumbnail thisEvent={test2}/>, <MovieThumbnail thisEvent={test2}/>]

   
    

    return (
        <>
        <div className={style.cinemaName}>{cinemaName}</div>
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