import React, { useEffect, useState } from "react";
import store from "../../../store";
import Pagination from "./Pagination";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import style from "../../../css/MyPage/MyPageDetail/MyPosts.module.css";

const MyPosts = () => {
    const navigation = useNavigate();
    
    const myPosts = useSelector(s => {
        if(s !== undefined) {
            return s.mypagePosts
        }
    })

    const [limit, setLimit] = useState(15);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        console.log("내가 쓴 글들 : ", myPosts);
    }, [myPosts])

    const postClick = (postid, e) => {
        navigation('/informationShare/'+postid);
    }

    //날짜 형식 바꾸기
    const parseDate = (written_date) => {
        const d = new Date(written_date);
        const year = d.getFullYear();
        let month = d.getMonth();
        let date = d.getDate();
        let hours = d.getHours();
        let min = d.getMinutes();
        if(month<10){
            month = '0'+month;
        }
        if(date<10){
            date = '0'+date;
        }
        if(hours<10){
            hours = '0'+hours;
        }
        if(min<10){
            min = '0'+min;
        }
        return (
            <div>{year}-{month}-{date} {hours} : {min}</div>
        )
    }

    return (
        <>
        <div className={style.layout}>
            <div className={style.topBar}>
                    <div>제목</div>
                    <div>카테고리</div>
                    <div>작성시간</div>
            </div>
            {myPosts !== undefined ? myPosts.slice(offset, offset + limit).map((item, index) => (
                <article key={index} onClick={e => postClick(item.post_id, e)}>
                    <div>{item.title}</div>
                    <div>{item.category}</div>
                    {
                        parseDate(item.written_date)
                    }
                </article>
            )) : null}
        </div>

        <footer className={style.footer}>
            {myPosts !== undefined ? 
            <Pagination total={myPosts.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyPosts;