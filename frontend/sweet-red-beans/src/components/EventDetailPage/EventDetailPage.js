import React, {useState} from "react";
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

    const data = {
        event_id: IDBKeyRange,
        cinema_name: "CGV",
        title: "해적 사은품 증정",
        detail_image_url: "이미지url",
        link_url: "http://www.cgv.co.kr/culture-event/event/detailViewUnited.aspx?seq=33955&menu=001",
        start_date: "2022-02-02",
        end_date: "2022-02-03",
        like_count: 10,
        is_like: true,
    }
    // const date = new Date()
    // console.log(date.getFullYear());

    axios.get('http://localhost:8080/events/detail', {
        params: {
            event_id: id,
        }
    })
    .then(response => {
        setThisEvent(response.data)
    })
    .catch(error => console.log(error));

    //서버 연결하면 data들 thisEvent로 바꾸기
    return (
        <>
        <h1>{id}, {data.title}</h1>
        <div>
            {data.start_date} ~ {data.end_date}
        </div>
        <div>
            {data.cinema_name}
        </div>
        <div>
            좋아요 수 : {data.like_count}
        </div>
        <div>
            {data.is_like ? <div>나의 좋아요 : O</div> : <div>나의 좋아요 : X</div>}
        </div>
        <div>
            {data.detail_image_url}
        </div>
        <div>
            <button onClick={() => window.open(data.link_url, '_blank')}>자세히 보기</button>
        </div>
        </>
    );
}

export default EventDetailPage;