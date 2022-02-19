import React, { useEffect, useState } from "react";
import MovieThumbnail from "./MovieThumbnail";
import axios from "axios";
import mainEvents from "../../actions/main_action";
import { useDispatch, useSelector } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";

const MainMovieEvents = ({cinemaName}) => {
    const dispatch = useDispatch();
    const [events, setEvents] = useState([]);
    const [thisEvents, setThisEvents] = useState([]);
    const filterMovieList = [];

    useEffect(()=>
    {
        axios.get('http://localhost:8080/main/event-limit')
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
    
    return (
        <>
        <h3>{cinemaName}</h3>
        {thisEvents!==[] ? thisEvents.map((item, index) => <div key={index}><MovieThumbnail thisEvent={item}/></div>) : null}
        </>
        
    );
}

export default MainMovieEvents;