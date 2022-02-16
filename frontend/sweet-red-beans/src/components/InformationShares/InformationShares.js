import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import Pagination from "./Pagination";
import style from "../../css/InformationShares.module.css"
import { useSelector } from "react-redux";

const InformationShares = ({infos}) => {
    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(()=>{

    },[])


    return (
    <div className={style.layout}>
        <label>
            페이지 당 표시할 게시물 수:&nbsp;
            <select
            type="number"
            value={limit}
            onChange={({ target: { value } }) => setLimit(Number(value))}
            >
            <option value="10">10</option>
            <option value="12">12</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
            </select>
        </label>
        <main>
            {infos.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            <Link to={`/informationShare/${item.post_id}`}>
            {item.title}
            </Link>
            </article>
        ))}
        </main>

        <footer>
            <Pagination total={infos.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
        </footer>
    </div>
    );
}

export default InformationShares;