import React, {useState, useEffect} from "react";
import { Routes, Route } from "react-router";
import { Link } from "react-router-dom";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import { useNavigate } from "react-router";
import EventPage from "./EventPage";
import EventMovieThumbnail from "./EventMovieThumbnail";
import { useSelector } from "react-redux";

const Events = ({events}) => {
    console.log("이벤트 보여주기 페이지 시작");

    useEffect(()=> {

    },[])


    return(
        <>
            <div>
            {events.map((item) => <div key={item.event_id}><EventMovieThumbnail event={item}/></div>)}

            </div>
    
        </>
    );
}

export default Events;