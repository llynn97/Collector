import React, {Fragment, useEffect, useState} from "react";
import { Link, Outlet } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import { Route, Routes } from "react-router";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import MainPage from "./MainPage";

//props랑 {cinemaName}이 똑같아야 함
const MovieThumbnail = ({cinemaName}) => {
    const dispatch = useDispatch();
    const [thisEvents, setThisEvents] = useState([]);
    const events = useSelector(state => state.mainEvents);
    const filterMovieList = [];

    useEffect(()=>
    {
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
    } 
    , []);


    return(
        <>
        <div>

            {thisEvents.map((item, index) => {
                return(
                    <Fragment key={index}>
                    <Link to = {`/event/${item.event_id}`}>
                    <div>{item.cinema_name}</div>
                    <div>{item.title}</div>
                    <div>{item.start_date} ~ {item.end_date}</div>
                    </Link>
                    <Outlet/>
                    </Fragment>   
                );  
            })}

            
        </div>
        
        </>
    );
}

export default MovieThumbnail;