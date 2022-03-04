import React, {useState, useEffect} from "react";
import { useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import events from "../../actions/event_action";
import axios from "axios";

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
             <>
             <h1>{id}, {title}</h1>
             <div>
                 {startDate} ~ {endDate}
             </div>
             <div>
                 {cinemaName}
             </div>
             <div>
                 좋아요 수 : {likeCount}
             </div>
             <div>
                 <button onClick={likeClick}>{status ? "좋아요o" : "좋아요x"}</button>
             </div>
             <div>
                 {   
                     imageUrl.map((item, index) => <img key={index} src={item}/>)
                 }
             </div>
             <div>
                 <button onClick={() => window.open(linkUrl, '_blank')}>자세히 보기</button>
             </div>
             </>
        : null}
        
        </>
    );
}

export default EventDetailPage;