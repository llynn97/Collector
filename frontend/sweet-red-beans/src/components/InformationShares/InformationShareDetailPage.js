import React, {useEffect, useMemo, useState}from "react";
import { Link, unstable_HistoryRouter } from "react-router-dom";
import Comment from "../Comment/Comment";
import { useParams } from "react-router";
import { useSelector } from "react-redux";
import axios from "axios";
import { useNavigate } from "react-router";

const InformationShareDetailPage = () => {
    console.log("정보공유 상세 페이지 렌더");
    let navigation = useNavigate();
    const {postid} = useParams();
    //const userid = useSelector(state => state.user.user_id);
    const [detailInfo, setDetailInfo] = useState({});
    const [comments, setComments] = useState([]);
    const [commentsIsHere, setCommentsIsHere] = useState(false);

    const[commentContent, setCommentContent] = useState("");

    //처음 조회
    useEffect(()=>{
        axios.get('http://localhost:8080/information-share/detail',{
        withCredentials: true,
        params: {
                    post_id : postid,
                    user_id : "1",
                }
        })
        .then(response => {
            console.log(response.data);
            const post = {
                post_id: response.data.post_id,
                title: response.data.title,
                written_date: response.data.written_date,
                content: response.data.content,
                views: response.data.views,
                nickname: response.data.nickname,
                image_url: response.data.image_url,
                cinema_name: response.data.cinema_name,
                cinema_area: response.data.cinema_area,
                cinema_branch: response.data.cinema_branch,
                is_mine: response.data.is_mine,
            }
            setDetailInfo(post); 
            setComments(response.data.comment)
        })
        .catch(error => console.log(error));

    },[])

    useEffect(()=>{
        setCommentsIsHere(true);
    }, [comments])

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
        axios.delete('http://localhost:8080/information-share/detail',{
        withCredentials: true,
        data: {
                    post_id : postid,
                    user_id : "1",
                }
        })
        .then(response => {
            if(response.data.result){
                alert("삭제되었습니다.")
                //이전 페이지로 이동
                navigation(-1)
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

    const commentContentChange = (e) => {
        setCommentContent(e.target.value);
    }

    //댓글쓰기 버튼 눌렀을 때
    const commentAddClick = () => {
        setCommentContent("");
        const body = {
            user_id: "1",
            content: commentContent,
            post_id: postid,
        }

        axios.post('http://localhost:8080/information-share/comment', body, { withCredentials: true })
        .then(response => {
            if(response.data.result){
                console.log("댓글 작성됨");
                //navigation(0)
            }
            else{
                alert("댓글 작성에 실패했습니다.");
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
        const month = d.getMonth();
        const date = d.getDate();
        const hours = d.getHours();
        const min = d.getMinutes();
        return (
            <div>{year}-{month}-{date}, {hours} : {min}</div>
        )
    }

    return(
        <>
        <h2>{detailInfo.title}</h2>
        <div>{detailInfo.nickname}, 
        {parseDate(detailInfo.written_date)}
        조회수 : {detailInfo.views}</div>
        <div>{detailInfo.cinema_name}, {detailInfo.cinema_area}, {detailInfo.cinema_branch}</div>
        <div>{detailInfo.image_url !== "" ? <img src={detailInfo.image_url}/> : null}</div>
        <div>{detailInfo.content}</div>
        <div>
            {detailInfo.is_mine ? <button onClick={deleteClick}>삭제</button> : null}
        </div>

        <hr/>

        <div>
            <div>닉네임</div>
            <input type="textarea" placeholder="댓글 쓰기" value={commentContent} onChange={commentContentChange}/>
            <button onClick={commentAddClick}>댓글 달기</button>
        </div>

        {commentsIsHere ? (
            comments.map((item, index) => <div key={index}><Comment comment={item}/></div>)
        ) : null}

        <div>
            <Link to={`/informationShare`}>
            <button>목록으로 돌아가기</button>
            </Link>
        </div>
        </>
    );
}

export default InformationShareDetailPage;