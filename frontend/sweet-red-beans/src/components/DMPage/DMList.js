import React, { useCallback, useState, useEffect } from "react";
import axios from "axios";
import DMListThumbnail from "./DMListThumbnail";

const DMList = () => {
    const [DMList, setDMList] = useState([]);

    useEffect(() => {
        //DM 목록 조회
        axios.get('http://localhost:8080/direct-message',{
        params: {
                    user_id:"1",
                }
        })
        .then(response => {
            setDMList(response.data)
        })
        .catch(error => console.log(error));
    }, [])

    //DMList가 바뀔 때마다 실행, 방 새로 만들었을 때 실행
    useEffect(() => {

    }, [DMList])

    return (
        <>
        <h2>DM 리스트</h2>
        {DMList.map((item, index) => (<div key={index}><DMListThumbnail dm={item}/></div>))}
        </>
    );
}

export default DMList;