import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import store from "../../../store";
import Pagination from "./Pagination";

const MyEvents = () => {
    const [storeData, setStoreData] = useState([]);
    const [myEvents, setMyEvents] = useState([]);

    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        //맨처음 스토어에서 데려오면 무조건 undefined
        setStoreData(store.getState());
        console.log(storeData);
    }, [])
    useEffect(() => {
        //그 후에 스토어에서 한 번 더 데려와서 set시켜주기
        setStoreData(store.getState());
        if(storeData !== undefined) {
            //가져온 거에 해딩 key가 있는지 확인
            setMyEvents(storeData.mypageEvents);
        }
    }, [storeData])

    useEffect(() => {
        console.log("내 이벤트들 : ", myEvents);
    }, [myEvents])

    return (
        <>
        {myEvents !== undefined ? myEvents.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            {item.event_title}
            </article>
        )) : null}

        <footer>
            {myEvents !== undefined ? 
            <Pagination total={myEvents.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyEvents;