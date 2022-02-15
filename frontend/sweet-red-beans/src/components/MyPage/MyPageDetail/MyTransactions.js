import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import store from "../../../store";
import TransactionDetail from "../../TransactionPage/TransactionDetail";
import Pagination from "./Pagination";

const MyTransactions = () => {
    const myTransactions = useSelector(s => {
        if(s !== undefined) {
            return s.mypageTransactions
        }
    })

    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        console.log("내가 쓴 거래들 : ", myTransactions);
    }, [myTransactions])

    return (
        <>
        {myTransactions !== undefined ? myTransactions.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            <TransactionDetail transaction={item}/>
            </article>
        )) : null}

        <footer>
            {myTransactions !== undefined ? 
            <Pagination total={myTransactions.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyTransactions;