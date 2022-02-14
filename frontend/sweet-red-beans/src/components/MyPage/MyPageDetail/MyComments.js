import React, { useEffect, useState } from "react";
import store from "../../../store";
import Pagination from "./Pagination";

const MyComments = () => {
    const [storeData, setStoreData] = useState([]);
    const [myComments, setMyComments] = useState([]);

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
            setMyComments(storeData.mypageComments);
        }
    }, [storeData])

    useEffect(() => {
        console.log("내 댓글들 : ", myComments);
    }, [myComments])

    return (
        <>
        {myComments !== undefined ? myComments.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            {item.comment_content}
            </article>
        )) : null}

        <footer>
            {myComments !== undefined ? 
            <Pagination total={myComments.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyComments;