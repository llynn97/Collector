import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import style from "../../css/Report/Report.module.css";
import Modal from "../Modals/Modal";

const Report = ({report}) => {
    const navigation = useNavigate();
    const [modalOpen, setModalOpen] = useState(false);
    const [content, setContent] = useState("");
    const [date, setDate] = useState("");
    const [nickname, setNickname] = useState("");
    const [reportedNickname, setReportedNickname] = useState("");
    const [isComplete, setIsComplete] = useState(false);

    const openModal = () => {
        setModalOpen(true);
    };
    const closeModal = () => {
        setModalOpen(false);
    };

    useEffect(() => {
        setContent(report.report_content);
        setDate(report.written_date)
        setNickname(report.nickname);
        setReportedNickname(report.reported_nickname)
        setIsComplete(report.is_complete);
        console.log(report);
    }, [report])

    const useConfirm = (message = null, onConfirm, onCancel) => {
        if (!onConfirm || typeof onConfirm !== "function") {
            return;
        }
        if (onCancel && typeof onCancel !== "function") {
            return;
        }
        
        const confirmAction = () => {
            if (window.confirm(message)) {
            onConfirm();
            } else {
            onCancel();
            }
        };
        
        return confirmAction;
    };

    const confirm = () => {
        axios.patch('http://localhost:8080/manager/report', {
            user_id:report.reported_nickname
        },
        {withCredentials: true}
        )
        .then((response) => {
        if(response.data){
            console.log(response.data)
        }
        })
        .catch((error) => {
            console.log(error);
        })
        closeModal();
        navigation(0);
    }

    const cancelConfirm = () => console.log("승인 취소")

    const reportAcceptClick = useConfirm(
        "승인하시겠습니까?",
        confirm,
        cancelConfirm
    );

    const parseDate = (written_date) => {
        const d = new Date(written_date);
        const year = d.getFullYear();
        let month = d.getMonth();
        let date = d.getDate();
        let hours = d.getHours();
        let min = d.getMinutes();
        if(month<10){
            month = '0'+month;
        }
        if(date<10){
            date = '0'+date;
        }
        if(hours<10){
            hours = '0'+hours;
        }
        if(min<10){
            min = '0'+min;
        }
        return (
            <div>{year}-{month}-{date} {hours} : {min}</div>
        )
    }

    return (
        <>
        <Modal open={modalOpen} close={closeModal}>
            <form className={style.modal}>
                <div className={style.topArea}>
                    <div>신고자 : {nickname}</div>
                    <div>{parseDate(date)}</div>
                </div>
                <div>신고 받은 사람 : {reportedNickname}</div>
                <div>{content}</div>
                <button onClick={reportAcceptClick}>승인하기</button>
            </form>
        </Modal>
        <div className={style.main} onClick={openModal}>
            <div className={isComplete ? style.complete : style.incomplete}>{isComplete ? "승인" : "미승인"}</div>
            <div>{nickname}</div>
            <div>{reportedNickname}</div>
            <div>{parseDate(date)}</div>
        </div>

        </>
    )
}

export default Report;