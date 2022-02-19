import React, {Fragment, useEffect, useState} from "react";
import { Link, Outlet } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import { Route, Routes } from "react-router";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import MainPage from "./MainPage";
import axios from "axios";

//props랑 {cinemaName}이 똑같아야 함
const MovieThumbnail = ({thisEvent}) => {
    //좋아요 상태 변경용
    const [status, setStatus] = useState(false);

    const likeClick = () => {
        const body = {
            user_id: "1",
            event_id: thisEvent.event_id,
        }
        axios.post('http://localhost:8080/events/like', body)
        .then(response => {
            if(response.data.result){
                if(status){
                    setStatus(false);
                }
                else {
                    setStatus(true);
                }
            }
            else {
                alert("삭제에 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
    }

    return(
        <>
        <div>

            <Fragment>
            <Link to = {`/event/${thisEvent.event_id}`}>
            <img src={thisEvent.thumbnail_url} width="300px" height="200px"/>
            <div>{thisEvent.cinema_name}</div>
            <div>{thisEvent.title}</div>
            <div>{thisEvent.start_date} ~ {thisEvent.end_date}</div>
            </Link>
            <div>
                <button onClick={likeClick}>{status ? "좋아요o" : "좋아요x"}</button>
            </div>
            </Fragment>   
            
        </div>
        
        </>
    );
}

export default MovieThumbnail;