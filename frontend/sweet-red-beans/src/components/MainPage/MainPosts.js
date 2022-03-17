import React, { useEffect, useState } from "react";
import axios from "axios";
import style from "../../css/MainPage/MainPosts.module.css";
import { Link } from "react-router-dom";

const MainPosts = () => {
    const [dailyPosts, setDailyPosts] = useState([]);

    const data = [
        {post_id : 12,
        title : "제목",
        content:"내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내내용내용내용내용내용내용내용내용내용내용내용내용용내용내용내용"},
        {post_id : 12,
        title : "제목",
        content:"내용내용내용내용내용내용"},
        {post_id : 12,
        title : "제목",
        content:"내용내용내용내용내용내용내용내용내용내용내용내용"},
        {post_id : 12,
        title : "제목",
        content:"내용내용내용내용내용내용내용내용내용내용내용내용"},
        {post_id : 12,
        title : "제목",
        content:"내용내용내용내용내용내용내용내용내용내용내용내용"}]
    
    useEffect(() => {
        axios.get("http://localhost:8080/main/daily-community", {
            withCredentials: true,
            params: {
                community_category:"정보공유",
            }
        })
        .then(response => {
            // console.log(response.data);
            // setDailyPosts(response.data);
        })
        .catch(error => console.log(error))

        setDailyPosts(data)
    }, [])

    return (
        <>
        <div className={style.posts}>
            <ul>
            {dailyPosts.map((item, index) => (
                <li key={index}>
                    <Link to={`/informationShare/${item.post_id}`} style={{textDecoration:"none"}}>
                        <div className={style.title}>{item.title}</div>
                    </Link>
                    <div>{item.content}</div>
                </li>
            ))}
            </ul>
        </div>
        
        </>
    )
}

export default MainPosts;