import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { DM_CREATE } from "../../actions/types";
import style from "../../css/TransactionPage/TransactionDetail.module.css";
import Modal from "../../components/Modals/ReportModal";

const TransactionDetail = ({transaction}) => {
    const dispatch = useDispatch();
    const navigation = useNavigate();
    const [status, setStatus] = useState("");
    const [likeStatus, setLikeStatus] = useState(false);
    const [loading, setLoading] = useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [reportContent, setReportContent] = useState("");

    const serverReqStatus = () => {
        const body = {
            user_id: "1",
            transaction_id: transaction.transaction_id,
            status: status,
        }
        axios.post('http://localhost:8080/transactions/change-status', body , { withCredentials: true })
        .then(response => {
            if(response.data.result){
            }
        })
        .catch(error => console.log(error))
    }

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

    //삭제버튼 삭제 눌렀을 때
    const deleteConfirm = () => {
        axios.delete('http://localhost:8080/transactions',{
        data: {
            user_id: "1",
            transaction_id:transaction.transaction_id,
        },
        withCredentials: true
        })
        .then(response => {
            if(response.data.result){
                navigation(0);
                alert("삭제되었습니다.")
            }
            else {
                alert("삭제에 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
    }

    //삭제 취소 버튼 눌렀을 때
    const cancelConfirm = () => console.log("취소")

    //삭제 버튼 클릭했을 때
    const deleteClick = useConfirm(
        "삭제하시겠습니까?",
        deleteConfirm,
        cancelConfirm
    );

    //마감으로 변경 눌렀을 때
    const statusClick = () => {
        if(status === "진행중"){
            serverReqStatus()
            setStatus("마감");
        }
        else if(status === "마감"){
            serverReqStatus()
            setStatus("진행중");
        }
    }

    const DMClick = () => {

        const body = {
            user_id : "1",
            transaction_id : transaction.transaction_id,
        }
        axios.post('http://localhost:8080/direct-message', body, { withCredentials: true })
        .then(response => {
            dispatch({
                type:DM_CREATE,
                DMCreate: response.data,
            })
            console.log(response.data);
            setLoading(true);
        })
        .catch(error => {
            if(error.response.status === 401){
                alert("로그인을 먼저 해주세요");
            }
        })

    }

    useEffect(() => {
        if(loading){
            navigation('/DM')
        }
        console.log("loading: ", loading);
    }, [loading]);

    //좋아요 눌렀을 때
    const likeClick = () => {
        const body = {
            user_id: "1",
            transaction_id: transaction.transaction_id,
        }
        axios.post('http://localhost:8080/transactions/like', body, { withCredentials: true })
        .then(response => {
            if(response.data.result){
                if(status){
                    setLikeStatus(false);
                }
                else {
                    setLikeStatus(true);
                }
            }
            else {
                alert("좋아요 변경에 실패했습니다.")
            }
        })
        .catch(error => {
            if(error.response.status === 401){
                alert("로그인을 먼저 해주세요");
            }
        });
    }

    //날짜 형식 바꾸기
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
            <div>{year}-{month}-{date}  {hours} : {min}</div>
        )
    }

    useEffect(() => {
        setStatus(transaction.status)
        setLikeStatus(transaction.is_like);
    },[transaction])

    const openModal = () => {
        setModalOpen(true);
    };
    const closeModal = () => {
        setModalOpen(false);
    };

    const reportContentChange = (e) => {
        setReportContent(e.target.value);
    }

    //신고 확인 버튼 눌렀을 때
    const reportConfirm = () => {
        const body = {
            user_id : "1",
            transaction_id : transaction.transaction_id,
            report_content : reportContent,
        }
        axios.post('http://localhost:8080/transactions/report', body)
        .then(response => {
            if(response.data.result){
                alert("신고되었습니다.")
                setReportContent("");
                setModalOpen(false);
            }
            else {
                alert("신고에 실패했습니다.")
            }
        })
        .catch(error => {
            if(error.response.status === 401){
                alert("로그인을 먼저 해주세요");
            }
        });
    }

    //모달창에서 신고하기 버튼 눌렀을 때
    const reportClick = (e) => {
        e.preventDefault();
        if(reportContent === ""){
            alert("신고 내용을 입력해주세요");
        }
        else {
            if (window.confirm("정말 신고하시겠습니까?")) {
                reportConfirm();
            } else {
                cancelConfirm();
            }
        }
    }

    return(
        <>
        <Modal open={modalOpen} close={closeModal} header="로그인">
        <form>
            신고사유를 적어주세요
            <div>
            <textarea value={reportContent} onChange={reportContentChange} style={{width:"400px", height:"200px", cols:"20"}}></textarea>
            <button onClick={reportClick}>신고하기</button>
            </div>
        </form>
        </Modal>

        <div className={style.transactionBox}>
            <div>
                <img src={transaction.profile_url} className={style.profileImage}/>
                <div className={style.nickname}>
                    {transaction.nickname} 
                </div>
                <div className={style.reliability}>
                <div className={style.reliabilityIcon}></div>
                <div className={style.reliabilityCount}>
                {transaction.reliability}
                </div>
                </div>

                {status === '진행중' ? <div className={style.proceeding}>진행중</div> : null}
                {status === '마감' ? <div className={style.done}>마감</div> : null}
                
                <div className={style.statusButtonArea}>
                {transaction.is_mine ? (<div>
                    <button className={style.statusButton} onClick={statusClick}>{status==="진행중" ? "마감으로 바꾸기" : "진행중으로 바꾸기"}</button>
                    </div>
                ) : 
                <div className={style.notMine}>
                    <button onClick={openModal} className={style.reportButton}></button>
                    {status === '진행중' ? <button onClick={DMClick} className={style.DMButton}></button> :
                    <button disabled={true} className={style.DMButton}></button>}
                    
                    <button onClick={likeClick} className={style.likeButton}>{likeStatus ? "" : ""}</button>
                </div>}
                </div>
                
            </div>
            <div>

                {transaction.is_mine ? (
                    <div className={style.deleteArea}>
                    <button onClick={deleteClick} className={style.deleteButton}></button>
                    </div>
                ) : null}
            </div>
            
            <div className={style.content}>
                {transaction.content}
            </div>

            <div className={style.date}>
            {
                parseDate(transaction.written_date)
            }
            </div>
        </div>

        
        </>
    );
}

export default TransactionDetail;