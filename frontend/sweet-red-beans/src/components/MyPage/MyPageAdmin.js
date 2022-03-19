import React, { useEffect, useState } from "react";
import Report from "../Report/Report";
import axios from "axios";
import Pagination from "../Pagination/Pagination";
import style from "../../css/MyPage/MyPageAdmin.module.css";

const MyPageAdmin = () => {
    const [reports, setReports] = useState([]);
    const [reportIsHere, setReportIsHere] = useState(false);

    const [limit, setLimit] = useState(15);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        axios.get('http://localhost:8080/manager/report', {withCredentials: true})
        .then(response => {
            
            setReports(response.data.reports);
        })
        .catch(error => console.log(error))

    }, [])

    useEffect(() => {
        setReportIsHere(true);
    }, [reports])
    
    return (
        <>
        <div className={style.layout}>
            <div className={style.reportBox}>
                <div className={style.topBar}>
                    <div>승인여부</div>
                    <div>신고자</div>
                    <div>신고 받은 사람</div>
                    <div>신고한 시간</div>
                </div>
                <main>
                {reportIsHere ? reports.slice(offset, offset + limit).map((item, index) => (
                    <article key={index}>
                        <Report report={item}/>
                    </article>
                )) : null}
                </main>
            </div>
            <footer>
                {reports !== undefined ? 
                <Pagination total={reports.length}
                limit={limit}
                page={page}
                setPage={setPage}/>
                : null
                }
            </footer>
        </div>

        </>
    )
}

export default MyPageAdmin;