import React, {useState, useEffect} from "react";
import { useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import events from "../../actions/event_action";
import axios from "axios";
import style from "../../css/EventPage/EventDetailPage.module.css";


const EventDetailPage = () => {
    const {id} = useParams();

    const [thisEvent, setThisEvent] = useState(null);
    const [thisEventIsHere, setThisEventIsHere] = useState(false);

    const [title, setTitle] = useState("");
    const [cinemaName, setCinemaName] = useState("");
    const [imageUrl, setImageUrl] = useState([]);
    const [linkUrl, setLinkUrl] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [likeCount, setLikeCount] = useState(0);

    //좋아요 상태 변경용
    const [status, setStatus] = useState(false);
    


    useEffect(()=>{
        axios.get('http://localhost:8080/events/detail', {
            withCredentials: true,
            params: {
                event_id: id,
            }
        })
        .then(response => {
            setThisEvent(response.data)
            setStatus(response.data.is_like);
            setTitle(response.data.title);
            setCinemaName(response.data.cinema_name);
            setImageUrl(response.data.detail_image_url)
            setLinkUrl(response.data.link_url)
            setStartDate(response.data.start_date)
            setEndDate(response.data.end_date)
            setLikeCount(response.data.like_count);
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

    useEffect(()=>{
        console.log(thisEvent)
        setThisEventIsHere(true);
    }, [thisEvent])
    

    return (
        <>
        {thisEventIsHere ? 
            <div className={style.whiteBox}>
            <div className={style.titleBox}>
                <div className={style.title}>{title}</div>
                <div className={style.date}>{startDate} ~ {endDate}</div>
            </div>
            <div className={style.cinemaBox}>
                <div className={style.cinemaName}>{cinemaName}</div>
                <div className={style.likeBox}>
                    <div>{status? <button onClick={likeClick} className={style.likeOnButton}></button> : <button onClick={likeClick} className={style.likeOffButton}></button>}</div>
                    <div>{likeCount}</div>
                </div>
            </div>
            
            <div>
                {   
                    imageUrl.map((item, index) => <img  className={style.imageBox} key={index} src={item}/>)
                }
            </div>
            <div className={style.linkBox}>
                <button className={style.linkButton} onClick={() => window.open(linkUrl, '_blank')}>자세히 보러 가기</button>
            </div>
            </div>
        : null}
        
        </>
    );
}

export default EventDetailPage;