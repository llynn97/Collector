import React, { useCallback, useState, useEffect } from "react";
import axios from "axios";
import DMListThumbnail from "./DMListThumbnail";
import DMDetail from "./DMDetail";
import { useDispatch } from "react-redux";
import { SELECTED_DM } from "../../actions/types";
import { useNavigate } from "react-router";

const DMList = ({DMListClick}) => {
    const [DMList, setDMList] = useState([]);
    const [selectedDM, setSelectedDM] = useState("");

    const navigation = useNavigate();
    const [renderError, setRenderError] = useState(false);
    
    const dispatch = useDispatch();

    useEffect(() => {
        //DM 목록 조회
        axios.get('http://localhost:8080/direct-message',{
        withCredentials: true,
        params: {
                }
        })
        .then(response => {
            console.log(response.data);
            setDMList(response.data.room_id)
        })
        .catch(error => {
            setRenderError(true);
            alert("로그인을 먼저 해주세요");
            navigation('/');
        });
    }, [])

    //DMList가 바뀔 때마다 실행, 방 새로 만들었을 때 실행
    useEffect(() => {

    }, [DMList])

    // const DMListClick = (selectedRoom, e) => {
    //     setSelectedDM(selectedRoom);
    //     // dispatch({
    //     //     type:SELECTED_DM,
    //     //     payload:selectedRoom,
    //     // })
    // }

    return (
        <>
        {!renderError ?
        DMList.map((item, index) => <div key={index} onClick={e => DMListClick(item, e)}><DMListThumbnail dm={item}/></div>): null}
        
        </>
    );
}

export default DMList;