import React, {useState} from "react";
import {Link} from "react-router-dom";
import axios from "axios";
import style from "../../css/EventPage/EventMovieThumbnail.module.css";

const EventMovieThumbnail = ({event}) => {
    //좋아요 상태 변경용
    const [status, setStatus] = useState(event.is_like);

    const likeClick = () => {
        const body = {
            user_id: "1",
            event_id: event.event_id,
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
            <Link to = {`/event/${event.event_id}`} style={{ textDecoration: 'none' }} className={style.thumbnailArea}>
                <img src={event.thumbnail_url}/>
                <div>{event.title}</div>
                <div>{event.start_date} ~ {event.end_date}</div>
            </Link>
            
            {status? <button onClick={likeClick} className={style.likeOnButton}></button> : <button onClick={likeClick} className={style.likeOffButton}></button>}

        </div>
        
        </>
    );
}

export default EventMovieThumbnail;