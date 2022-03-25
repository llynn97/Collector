import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Pagination from '../Pagination/Pagination';
import style from '../../css/GeneralBoardPage/GeneralBoard.module.css';
import axios from 'axios';
import { parseDate } from '../../parseDate/parseDate';

const GeneralBoard = ({ searchWords, sort }) => {
    const [infos, setInfos] = useState([]);
    const [limit, setLimit] = useState(15);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        //처음에 최신순으로 요청
        axios
            .get('http://localhost:8080/general-board/search', {
                withCredentials: true,
                params: {
                    search_word: '',
                    search_criteria: sort,
                },
            })
            .then((response) => setInfos(response.data))
            .catch((error) => console.log(error));

        return () => {
            setInfos([]);
        };
    }, []);

    useEffect(() => {
        let searchWord = '';
        if (searchWords.length !== 0) {
            searchWord = searchWords[searchWords.length - 1];
            console.log(searchWord);
        }

        axios
            .get('http://localhost:8080/general-board/search', {
                withCredentials: true,
                params: {
                    search_word: searchWord,
                    search_criteria: sort,
                },
            })
            .then((response) => setInfos(response.data))
            .catch((error) => console.log(error));
    }, [searchWords]);

    return (
        <div className={style.layout}>
            {}
            <div className={style.infoBox}>
                <div className={style.topBar}>
                    <div>제목</div>
                    <div>글쓴이</div>
                    <div>작성시간</div>
                    <div>조회수</div>
                </div>
                <main>
                    {infos.slice(offset, offset + limit).map((item, index) => (
                        <article key={index}>
                            <Link
                                to={`/community/general/${item.post_id}`}
                                style={{ textDecoration: 'none' }}>
                                <div>{item.title}</div>
                            </Link>
                            <div>
                                {item.user_status === '정지' ||
                                item.user_status === '탈퇴'
                                    ? '(알수없음)'
                                    : item.nickname}
                            </div>
                            <div>{parseDate(item.written_date)}</div>
                            <div>{item.view}</div>
                        </article>
                    ))}
                </main>
            </div>

            <div className={style.writeButtonArea}>
                <Link to={`/community/generalWrite`}>
                    <button>글쓰기</button>
                </Link>
            </div>

            <footer>
                <Pagination
                    total={infos.length}
                    limit={limit}
                    page={page}
                    setPage={setPage}
                />
            </footer>
        </div>
    );
};

export default GeneralBoard;
