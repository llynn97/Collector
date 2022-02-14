import React, {Fragment, useEffect, useState} from "react";
import { Link, Outlet } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import { Route, Routes } from "react-router";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import MainPage from "./MainPage";
import axios from "axios";

//props랑 {cinemaName}이 똑같아야 함
const MovieThumbnail = ({cinemaName}) => {
    const dispatch = useDispatch();
    const [thisEvents, setThisEvents] = useState([]);
    const [events, setEvents] = useState([]);
    //const events = useSelector(state => state.mainEvents);
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


    return(
        <>
        <div>

            {thisEvents.map((item, index) => {
                return(
                    <Fragment key={index}>
                    <Link to = {`/event/${item.event_id}`}>
                    <img src={item.thumbnail_url} width="300px" height="200px"/>
                    <div>{item.cinema_name}</div>
                    <div>{item.title}</div>
                    <div>{item.start_date} ~ {item.end_date}</div>
                    </Link>
                    <div>
                        {item.is_like ? <div>좋아요O</div> : <div>좋아요X</div>}
                    </div>
                    </Fragment>   
                );  
            })}

            
        </div>
        
        </>
    );
}

export default MovieThumbnail;