import React, { useCallback, useState, useEffect } from "react";
import axios from "axios";
import DMListThumbnail from "./DMListThumbnail";
import DMDetail from "./DMDetail";
import { useDispatch } from "react-redux";
import { SELECTED_DM } from "../../actions/types";

const DMList = ({DMListClick}) => {
    const [DMList, setDMList] = useState([]);
    const [selectedDM, setSelectedDM] = useState("");
    
    const dispatch = useDispatch();

    useEffect(() => {
        //DM 목록 조회
        axios.post('http://localhost:8080/direct-message',{
        params: {
                    user_id:"1",
                }
        }, { withCredentials: true })
        .then(response => {
            console.log(response.data);
            setDMList(response.data.room_id)
        })
        .catch(error => console.log(error));
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
        <h2>DM 리스트</h2>
        {DMList.map((item, index) => <div key={index} onClick={e => DMListClick(item, e)}><DMListThumbnail dm={item}/></div>)}
        </>
    );
}

export default DMList;