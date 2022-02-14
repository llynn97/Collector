import React, { useEffect, useState } from "react";
import store from "../../../store";
import Pagination from "./Pagination";

const MyLikeTransactions = () => {
    const [storeData, setStoreData] = useState([]);
    const [myLikeTransactions, setMyLikeTransactions] = useState([]);

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
            setMyLikeTransactions(storeData.mypageLikeTransactions);
        }
    }, [storeData])

    useEffect(() => {
        console.log("내가 좋아요 한 거래들 : ", myLikeTransactions);
    }, [myLikeTransactions])

    return (
        <>
        {myLikeTransactions !== undefined ? myLikeTransactions.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            {item.content}
            </article>
        )) : null}

        <footer>
            {myLikeTransactions !== undefined ? 
            <Pagination total={myLikeTransactions.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyLikeTransactions;