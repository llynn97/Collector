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

const EventPage = () => {
    console.log("이벤트 페이지 렌더");
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

    const serverReq = () => {
        axios.get('http://localhost:8080/events/search',{
        params: {
                    //cinema_name: selected,
                    sort_criteria: sort,
                    is_end: isEnd,
                    search_word: search,
                }
        })
        .then(response => {setEvents(response.data)
        console.log(response.data);})
        .catch(error => console.log(error));
    }

    //검색
    const searchChange = (e) => {
        setSearch(e.target.value);
    }
    
    //검색 버튼 눌렀을 때 다시 서버 요청
    const searchClick = () => {
        setSearchWords([...searchWords, search]);
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

    //영화관 필터 검색 버튼 눌렀을 때
    const cinemaNameSearch = () => {
        if (selected === selectList[0]){
            alert("영화관을 선택해주세요")
        }
        else {
            console.log(selected);
            setSelecteds([...selecteds, selected]);
        }
    }
    
    useEffect(()=>{
        //진행 중/완료 바뀔 때마다 실행
        //console.log(isEnd);
        serverReq();
    }, [isEnd])

    useEffect(()=>{
        //최신순, 관심도순 바뀔 때마다 실행
        //console.log(sort);
        serverReq();
        console.log(sort);
        console.log(eventIsHere);
    }, [sort])  

    useEffect(()=>{
        //검색 버튼 눌렀을 때마다 실행
        console.log(searchWords);
        serverReq();
    }, [searchWords])

    useEffect(()=>{
        //영화관 필터 바뀌었을 때마다 실행
        //console.log(selected);
    }, [selected]);

    useEffect(()=>{
        //영화관 필터 검색 버튼 눌렀을 때마다 실행
        //console.log(selecteds);
        serverReq();
    }, [selecteds]);

    useEffect(()=>{
        //처음에 기본으로 진행 중인 이벤트로 보여줌
        dispatch({
            type:EVENT_ISEND,
            payload:false,
        })
        
        //처음에 기본으로 최신순으로 보여줌
        dispatch({
            type:EVENT_SORT,
            payload:sort,
        })
        
        //처음에 진행 중, 최신순으로 요청
        axios.get('http://localhost:8080/events/search',{
        params: {
                    sort_criteria: sort,
                    is_end: isEnd,
                    search_word: search,
                }
        })
        .then(response => setEvents(response.data))
        .catch(error => console.log(error));

    }, [])

    useEffect(()=> {
        dispatch({
            type:EVENTS,
            events:events,
        });
        setEventIsHere(true)
        console.log(eventIsHere);
    },[events])

    return(
        <>
        <div>
            <div>
                {}
                <button onClick={ongoingClick}>진행 중 이벤트</button>
                <button onClick={doneClick}>완료된 이벤트</button>
            </div>
            <div id="align">
                <button onClick={recentClick}>최신순</button>
                <button onClick={interestClick}>관심도순</button>
            </div>
            <select onChange={selectChange}>
                {selectList.map((item) => (
                    <option value={item} key={item}>{item}</option>
                ))}
            </select>
            <button id="searchButton" onClick={cinemaNameSearch}>검색</button>

            <div>
                <input type="text" placeholder="검색" onChange={searchChange} value={search}></input>
                <button id="searchButton" onClick={searchClick}>검색</button>
            </div>
            {//검색버튼 눌렀을 때, 진행중/완료 선택했을 때, 최신순/관심도 순 선택했을 때, 영화관 선택 버튼 눌렀을 때만 Events re-render
            useMemo(() => eventIsHere ? <Events events={events}/>:null, [searchWords, isEnd, sort, selecteds, events])}
        
        </div>
        
        </>
        
    );
}

export default EventPage;