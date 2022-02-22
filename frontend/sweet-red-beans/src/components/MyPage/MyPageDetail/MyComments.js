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
        ;
        console.log(new Date("2022-02-21T22:29:34.564").getFullYear());
    }, [myComments])

    //날짜 형식 바꾸기
    const parseDate = (written_date) => {
        const d = new Date(written_date);
        const year = d.getFullYear();
        const month = d.getMonth();
        const date = d.getDate();
        const hours = d.getHours();
        const min = d.getMinutes();
        return (
            <div>{year}-{month}-{date}, {hours} : {min}</div>
        )
    }

    return (
        <>
        {myComments !== undefined ? myComments.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            {item.comment_content}, 
            {
                parseDate(item.written_date)
            }
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