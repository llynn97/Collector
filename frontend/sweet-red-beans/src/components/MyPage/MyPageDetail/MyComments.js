import React, { useEffect, useState } from "react";
import store from "../../../store";
import Pagination from "./Pagination";
import { useSelector } from "react-redux";

const MyComments = () => {
    const myComments = useSelector(s => {
        if(s !== undefined) {
            return s.mypageComments
        }
    })
    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;


    useEffect(() => {
        console.log("내 댓글들 : ", myComments);
    }, [myComments])

    return (
        <>
        {myComments !== undefined ? myComments.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            {item.comment_content}, 
            {item.written_date}
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