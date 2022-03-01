import React, {useState, useEffect} from "react";
import { useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import events from "../../actions/event_action";
import axios from "axios";

const EventDetailPage = () => {
    const {id} = useParams();

    const [thisEvent, setThisEvent] = useState({});
    //좋아요 상태 변경용
    const [status, setStatus] = useState(false);


    useEffect(()=>{
        axios.post('http://localhost:8080/events/detail', {
            params: {
                event_id: id,
            }
        }, { withCredentials: true })
        .then(response => {
            setThisEvent(response.data)
            setStatus(response.data.is_like);
        })
        .catch(error => console.log(error));
    }, [])

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
            <button onClick={likeClick}>{status ? "좋아요o" : "좋아요x"}</button>
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