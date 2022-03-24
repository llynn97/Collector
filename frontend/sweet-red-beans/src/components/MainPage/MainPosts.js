import React, { useEffect, useState } from 'react';
import axios from 'axios';
import style from '../../css/MainPage/MainPosts.module.css';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router';

const MainPosts = () => {
    const navigation = useNavigate();
    const [dailyPosts, setDailyPosts] = useState([]);

    useEffect(() => {
        axios
            .get('http://localhost:8080/main/daily-community', {
                withCredentials: true,
                params: {
                    community_category: '정보공유',
                },
            })
            .then((response) => {
                console.log(response.data);
                setDailyPosts(response.data);
            })
            .catch((error) => console.log(error));
    }, []);

    const writeClick = () => {
        navigation('/informationShare');
    };

    return (
        <>
            <div className={style.posts}>
                {dailyPosts.length !== 0 ? (
                    <ul>
                        {dailyPosts.map((item, index) => (
                            <Link
                                to={`/informationShare/${item.post_id}`}
                                style={{ textDecoration: 'none' }}
                                key={index}>
                                <li>
                                    <div className={style.title}>
                                        {item.title}
                                    </div>
                                </li>
                            </Link>
                        ))}
                    </ul>
                ) : (
                    <div className={style.nullMessage}>
                        <div>지금 당장 글 쓰러 가기</div>
                        <div className={style.arrow} onClick={writeClick}></div>
                        <div>내 통장처럼 비어버린 오늘의 글...</div>
                    </div>
                )}
            </div>
        </>
    );
};

export default MainPosts;
