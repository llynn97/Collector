import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import style from "../../css/Comment/Comment.module.css";

const Comment = ({comment}) => {
    console.log("댓글 렌더");
    const navigation = useNavigate();

    //const userid = useSelector(state => state.user.user_id);

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

    //삭제버튼 눌렀을 때
    const deleteConfirm = () => {
        axios.delete('http://localhost:8080/information-share/comment',{
        withCredentials: true,
        data: {
                    user_id : "1",
                    comment_id: comment.comment_id,
                }
        })
        .then(response => {
            if(response.data.result){
                alert("삭제되었습니다.")
                //현재 페이지 리렌더
                navigation(0)
            }
            else {
                alert("삭제에 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
    }

    const cancelConfirm = () => console.log("삭제 취소")

    const deleteClick = useConfirm(
        "삭제하시겠습니까?",
        deleteConfirm,
        cancelConfirm
    );

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
            `${year}-${month}-${date} ${hours} : ${min}`
        )
    }

    console.log(comment.comment_nickname);
    return (
        <>
        <div className={style.comment}>
            <div className={style.topBar}>
                <div>{comment.comment_nickname}</div>
                <div>{parseDate(comment.comment_written_date)}</div>
            </div>
            
            <div className={style.contentArea}>
                <div>{comment.comment_content}</div>
                <div>
                    {comment.is_mine ? <button onClick={deleteClick}>삭제</button> : null}
                </div>
            </div>
            
        </div>
        
        
        </>
    );
}

export default Comment;