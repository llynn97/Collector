import axios from "axios";
import React, { useEffect, useState } from "react";

const Report = ({report}) => {
    const [content, setContent] = useState("");
    const [date, setDate] = useState("");
    const [nickname, setNickname] = useState("");
    const [disable, setDisable] = useState(false);

    useEffect(() => {
        setContent(report.report_content);
        setDate(report.written_date)
        setNickname(report.nickname);
    }, [])

    const reportAcceptClick = () => {
        axios.patch('http://localhost:8080/manager/report', {
            user_id:report.user_id,
        })
        .then((response) => {
        if(response.data){
            setDisable(true);
            console.log(response.data)
        }
        })
        .catch((error) => {
            console.log(error);
        })
    }

    return (
        <>
        <div>{content}</div>
        <div>{date}</div>
        <div>{nickname}</div>
        <div>
            <button onClick={reportAcceptClick} disabled={disable}>승인 처리하기</button>
        </div>
        </>
    )
}

export default Report;