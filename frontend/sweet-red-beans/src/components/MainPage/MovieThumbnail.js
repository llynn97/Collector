import React, {Fragment, useEffect, useState} from "react";
import { Link, Outlet } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { MAIN_CINEMA_EVENTS } from "../../actions/types";
import { Route, Routes } from "react-router";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import MainPage from "./MainPage";
import axios from "axios";
import style from "../../css/MainPage/MovieThumbnail.module.css";

//props랑 {cinemaName}이 똑같아야 함
const MovieThumbnail = ({thisEvent}) => {
    //좋아요 상태 변경용
    const [status, setStatus] = useState(false);

    const likeClick = () => {
        const body = {
            user_id: "1",
            event_id: thisEvent.event_id,
        }
        axios.post('http://localhost:8080/events/like', body, { withCredentials: true })
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
        <div className={style.container}>

            <Link to = {`/event/${thisEvent.event_id}`} style={{ textDecoration: 'none' }}>
            <div className={style.thumbnailArea}>
            <img src={thisEvent.thumbnail_url} className={style.thumbnailImage}/>
            </div>
            
            <div className={style.title}>{thisEvent.title}</div>
            <div className={style.date}>{thisEvent.start_date} ~ {thisEvent.end_date}</div>
            </Link>
            <div>
                {status? <button onClick={likeClick} className={style.likeOnButton}></button> : <button onClick={likeClick} className={style.likeOffButton}></button>}
            </div>
        </div>
        
        </>
    );
}

export default MovieThumbnail;