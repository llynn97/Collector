import React, { useCallback, useState, useEffect } from "react";
import axios from "axios";
import DMListThumbnail from "./DMListThumbnail";
import DMDetail from "./DMDetail";
import { useDispatch } from "react-redux";
import { SELECTED_DM } from "../../actions/types";
import { useNavigate } from "react-router";

const DMList = ({DMlist, DMListClick}) => {
    //const [DMList, setDMList] = useState([]);
    const [selectedDM, setSelectedDM] = useState("");

    const navigation = useNavigate();
    const [renderError, setRenderError] = useState(true);
    
    const dispatch = useDispatch();

    useEffect(() => {
        //DM 목록 조회
        // axios.get('http://localhost:8080/direct-message',{
        // withCredentials: true,
        // params: {
        //         }
        // })
        // .then(response => {
        //     console.log(response.data);
        //     setDMList(response.data.room_id)
        //     setRenderError(false);
        // })
        // .catch(error => {
        //     alert("로그인을 먼저 해주세요");
        //     setRenderError(true);
        //     navigation('/');
        // });
    }, [])

    return (
        <>
        {
        DMlist.map((item, index) => <div key={index} onClick={e => DMListClick(item, e)}><DMListThumbnail dm={item}/></div>)}
        
        </>
    );
}

export default DMList;