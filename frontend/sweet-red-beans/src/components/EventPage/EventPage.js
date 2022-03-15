import React, {useCallback, useEffect, useState, useMemo} from "react";
import { Routes, Route } from "react-router";
import { Link } from "react-router-dom";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import { useNavigate } from "react-router";
import Events from "./Events";
import { useDispatch, useSelector, shallowEqual } from "react-redux";
import events from "../../actions/event_action";
import { EVENT_ISEND, EVENT_SORT, EVENTS } from "../../actions/types";
import axios from "axios";
import style from "../../css/EventPage/EventPage.module.css";

const EventPage = () => {
    
    let navigation = useNavigate();
    const dispatch = useDispatch();

    const [events, setEvents] = useState([]); 
    const [eventIsHere, setEventIsHere] = useState(false);
    //검색 단어, 처음에 없음
    const [search, setSearch] = useState("");
    //검색했던 단어들 저장
    const [searchWords, setSearchWords] = useState([]);
    //진행중이면 false
    const [isEnd, setIsEnd] = useState(false);
    const selectList = ["전체", "CGV", "롯데시네마", "메가박스", "씨네큐"];
    //선택된 시네마 이름, 처음엔 영화관
    const [selected, setSelected] = useState("전체");
    //선택했던 시네마 이름들 저장
    const [selecteds, setSelecteds] = useState([]);
    //최신순(recent) or 관심도순(interest)
    const [sort, setSort] = useState("최신순");

    //검색
    const searchChange = (e) => {
        setSearch(e.target.value);
    }
    
    //검색 버튼 눌렀을 때 다시 서버 요청
    const searchClick = () => {
        setSearchWords([...searchWords, search]);
        setSelecteds([...selecteds, selected]);
    }

    //진행 중 클릭했을 때 다시 서버 요청
    const ongoingClick = () => {
        setIsEnd(false);
        dispatch({
            type:EVENT_ISEND,
            payload: false,
        })
    }

    //진행 완료 클릭했을 때 다시 서버 요청
    const doneClick = () => {
        setIsEnd(true);
        dispatch({
            type:EVENT_ISEND,
            payload: true,
        })
    }

    //최신순 클릭했을 때 다시 서버 요청
    const recentClick = () => {
        setSort("최신순");
        dispatch({
            type:EVENT_SORT,
            payload:"최신순",
        })
    }

    //관심도순 클릭했을 때 다시 서버 요청
    const interestClick = () =>{
        setSort("관심도순");
        dispatch({
            type:EVENT_SORT,
            payload:"관심도순",
        })
    }

    //영화관 필터 바뀌었을 때
    const selectChange = (e) => {
        setSelected(e.target.value);
    }


    useEffect(()=>{
        // //처음에 기본으로 진행 중인 이벤트로 보여줌
        // dispatch({
        //     type:EVENT_ISEND,
        //     payload:false,
        // })
        
        // //처음에 기본으로 최신순으로 보여줌
        // dispatch({
        //     type:EVENT_SORT,
        //     payload:sort,
        // })


    }, [])

    useEffect(()=> {
        // dispatch({
        //     type:EVENTS,
        //     events:events,
        // });
        setEventIsHere(true)
    },[events])

    return(
        <>
        <div className={style.whiteBox}>
            <div className={style.topBar}>
                <div className={style.datefilter}>
                    <button onClick={ongoingClick} className={isEnd?style.notSelected:style.selected}>진행 중</button>
                    <button onClick={doneClick} className={!isEnd?style.notSelected:style.selected}>진행 완료</button>
                </div>
                <div id="align" className={style.filter}>
                    <button onClick={recentClick} className={sort==="최신순"?style.selected:style.notSelected}>최신순</button>
                    <button onClick={interestClick} className={sort==="관심도순"?style.selected:style.notSelected}>관심도순</button>
                </div>
                <div className={style.cinemafilter}>
                    <select onChange={selectChange} value={selected}>
                        {selectList.map((item) => (
                            <option value={item} key={item}>{item}</option>
                        ))}
                    </select>
                    <span className={style.filterArrow}></span>
                </div>
                

                <div className={style.search}>
                    <input type="text" placeholder="검색" onChange={searchChange} value={search}></input>
                    <div className={style.underline}></div>
                    <button id="searchButton" onClick={searchClick}></button>
                </div>
            </div>
            


            {
            <Events sort={sort} isEnd={isEnd} search_word={searchWords} cinema_name={selecteds}/>
            }
        
        </div>
        
        </>
        
    );
}

export default EventPage;