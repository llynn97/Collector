import React, { useEffect, useState } from "react";
import Report from "../Report/Report";
import axios from "axios";
import Pagination from "./MyPageDetail/Pagination";

const MyPageAdmin = () => {
    const [reports, setReports] = useState([]);
    const [reportIsHere, setReportIsHere] = useState(false);

    const [limit, setLimit] = useState(10);
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
        {reportIsHere ? reports.slice(offset, offset + limit).map((item, index) => (
            <article key={index}>
            <Report report={item}/>
            </article>
        )) : null}

        <footer>
            {reports !== undefined ? 
            <Pagination total={reports.length}
            limit={limit}
            page={page}
            setPage={setPage}/>
            : null
            }
        </footer>
        </>
    )
}

export default MyPageAdmin;