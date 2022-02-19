import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { DM_CREATE } from "../../actions/types";

const TransactionDetail = ({transaction}) => {
    const dispatch = useDispatch();
    const navigation = useNavigate();
    const [status, setStatus] = useState(transaction.status);
    const [likeStatus, setLikeStatus] = useState(transaction.is_like);
    const [loading, setLoading] = useState(false);

    const serverReqStatus = () => {
        const body = {
            user_id: "1",
            transaction_id: transaction.transaction_id,
            status: status,
        }
        axios.post('http://localhost:8080/transactions/change-status', body)
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
                }
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
    const cancelConfirm = () => console.log("삭제 취소")

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
        axios.post('http://localhost:8080/direct-message', body)
        .then(response => {
            dispatch({
                type:DM_CREATE,
                DMCreate: response.data,
            })
            console.log(response.data);
            setLoading(true);
        })
        .catch(error => console.log(error))

    }

    useEffect(() => {
        if(loading){
            navigation('/DM')
        }
        console.log("loading: ", loading);
    }, [loading]);

    const likeClick = () => {
        const body = {
            user_id: "1",
            transaction_id: transaction.transaction_id,
        }
        axios.post('http://localhost:8080/transactions/like', body)
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
        .catch(error => console.log(error));
    }

    return(
        <>
        <h3>거래글</h3>
        <div>
            {status === "진행중" ? <div>진행중</div> : <div>마감</div>}
            
            {transaction.nickname} & 신뢰도 : {transaction.reliability}
            {transaction.is_mine ? null : <button onClick={DMClick}>DM</button>}
            <button onClick={likeClick}>{likeStatus ? "좋아요o" : "좋아요x"}</button>
        </div>
        <div>
            {transaction.content}
        </div>
        {transaction.is_mine ? (
            <div>
            <button onClick={deleteClick}>삭제</button>
            {status === "진행중" ? <button onClick={statusClick}>마감으로 바꾸기</button> : <button onClick={statusClick}>진행중으로 바꾸기</button>}
            </div>
         ) : null}
        
        </>
    );
}

export default TransactionDetail;