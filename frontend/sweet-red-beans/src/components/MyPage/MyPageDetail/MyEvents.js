import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import store from "../../../store";
import Pagination from "./Pagination";
import { useNavigate } from "react-router";
import style from "../../../css/MyPage/MyPageDetail/MyEvents.module.css";

const MyEvents = () => {
    const navigation = useNavigate();
    
    const myEvents = useSelector(s => {
        if(s !== undefined) {
            return s.mypageEvents
        }
    })

    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        console.log("내 이벤트들 : ", myEvents);
    }, [myEvents])

    const eventClick = (eventid, e) => {
        navigation('/event/'+eventid);
    }

    return (
        <>
        {myEvents !== undefined ? myEvents.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
                <div onClick={e => eventClick(item.event_id, e)}>
                    <img src={item.thumbnail_url} width="200px" height="200px"/>
                </div>
                {item.event_title}
            </article>
        )) : null}

        <footer className={style.footer}>
            {myEvents !== undefined ? 
            <Pagination total={myEvents.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyEvents;