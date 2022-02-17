import React, {useState, useEffect} from "react";
import { useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import events from "../../actions/event_action";
import axios from "axios";

const EventDetailPage = () => {
    const {id} = useParams();
    const dispatch = useDispatch();
    const [events, setEvents] = useState([]);
    const [cinemaName, setCinemaName] = useState([]);
    const [eventId, setEventId] = useState([]);
    const [thumbnailUrl, setThumbnailUrl] = useState([]);
    const [title, setTitle] = useState([]);
    const [startDate, setStartDate] = useState([]);
    const [endDate, setEndDate] = useState([]);
    const [isLike, setIsLike] = useState([]);

    const [thisEvent, setThisEvent] = useState({});


    useEffect(()=>{
        axios.get('http://localhost:8080/events/detail', {
            params: {
                event_id: id,
            }
        })
        .then(response => {
            setThisEvent(response.data)
        })
        .catch(error => console.log(error));
    }, [])

    

    //서버 연결하면 data들 thisEvent로 바꾸기
    return (
        <>
        <h1>{id}, {thisEvent.title}</h1>
        <div>
            {thisEvent.start_date} ~ {thisEvent.end_date}
        </div>
        <div>
            {thisEvent.cinema_name}
        </div>
        <div>
            좋아요 수 : {thisEvent.like_count}
        </div>
        <div>
            {thisEvent.is_like ? <button>나의 좋아요 : O</button> : <button>나의 좋아요 : X</button>}
        </div>
        <div>
            <img src={thisEvent.detail_image_url}/>
        </div>
        <div>
            <button onClick={() => window.open(thisEvent.link_url, '_blank')}>자세히 보기</button>
        </div>
        </>
    );
}

export default EventDetailPage;