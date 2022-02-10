import React from "react";
import axios from "axios";
import { useNavigate } from "react-router";

const TransactionDetail = ({transaction}) => {
    const navigation = useNavigate();

    const deleteClick = () => {
        axios.delete('http://localhost:8080/transactions',{
        params: {
                    user_id: "1",
                    transaction_id:transaction.transaction_id,
                }
        })
        .then(response => {
            if(response.data.result){
                alert("삭제되었습니다.")
            }
            else {
                alert("삭제에 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
    }
    return(
        <>
        <h3>거래글</h3>
        <div>
            {transaction.status === "진행중" ? <div>진행중</div> : <div>마감</div>}
            {transaction.nickname}
            {transaction.is_mine ? null : <button>DM</button>}
        </div>
        <div>
            {transaction.content}
        </div>
        {transaction.is_mine ? (
            <div>
            <button onClick={deleteClick}>삭제</button>
            <button>마감으로 바꾸기</button>
            </div>
         ) : null}
        
        </>
    );
}

export default TransactionDetail;