import React, {useState} from "react";
import {Link} from "react-router-dom";
import axios from "axios";

const EventMovieThumbnail = ({event}) => {
    //좋아요 상태 변경용
    const [status, setStatus] = useState(event.is_like);

    const likeClick = () => {
        const body = {
            user_id: "1",
            event_id: event.event_id,
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
        <Link to = {`/event/${event.event_id}`}>
        <h3>
            <img src={event.thumbnail_url} width="300px" height="200px"/>
        </h3>
        <div>
            {event.title}, {event.start_date} ~ {event.end_date}, {event.cinema_name}
        </div>
        </Link>
        <div>
            <button onClick={likeClick}>{status ? "좋아요o" : "좋아요x"}</button>
        </div>
        
        </>
    );
}

export default EventMovieThumbnail;