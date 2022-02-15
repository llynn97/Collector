import React, { useEffect, useState } from "react";
import MyPageDetail from "./MyPageDetail";
import axios from "axios";
import { useDispatch } from "react-redux";
import { MYPAGE_USER, MYPAGE_TRANSACTIONS, MYPAGE_COMMENTS, MYPAGE_EVENTS, MYPAGE_POSTS, MYPAGE_LIKE_TRANSACTIONS } from "../../actions/types";

const MyPageNomal = () => {
    const [profileImage, setProfileImage] = useState("");
    const [nickname, setNickname] = useState("");
    const [reliability, setReliability] = useState("");
    const [myMenu, setMyMenu] = useState(0); 
    const myList = ["내가 관심있는 이벤트", "내가 쓴 거래", "내가 좋아요 한 거래", "내가 쓴 글", "내가 쓴 댓글"];

    const [hide, setHide] = useState(true);

    const [imgFile64, setImgFile64] = useState([]);
    const [imgFile, setImgFile] = useState(null);

    //닉네임 중복 확인이 성공적으로 됐을 때 true
    const [nicknameCheck, setNicknameCheck] = useState(false);
    //닉네임 중복 확인 안 됐을 때 false
    const [nicknameError, setNicknameError] = useState(false);

    const dispatch = useDispatch();

    useEffect(() => {
    //     axios.get('http://localhost:8080/mypage',{
    //     params: {
    //                 user_id: "1",
    //             }
    //     })
    //     .then(response => {
            // setNickname(response.data.user.nickname);
            // setProfileImage(response.data.user.profile_url);
            // setReliability(response.data.user.reliability);
            // dispatch({
            //     type:MYPAGE_USER,
            //     payload:response.data.user
            // })
    //         dispatch({
    //             type:MYPAGE_TRANSACTIONS,
    //             payload:response.data.writeTransaction
    //         })
    //         dispatch({
    //             type:MYPAGE_COMMENTS,
    //             payload:response.data.comment
    //         })
    //         dispatch({
    //             type:MYPAGE_EVENTS,
    //             payload:response.data.likeEvent
    //         })
    //         dispatch({
    //             type:MYPAGE_POSTS,
    //             payload:response.data.content
    //         })
    //         dispatch({
    //             type:MYPAGE_LIKE_TRANSACTIONS,
    //             payload:response.data.likeTransaction
    //         })
    //     })
    //     .catch(error => console.log(error));

    setNickname("현정");
    setProfileImage("https://w.namu.la/s/43a3472858577498e23c3701af9afad33de29d4a6235e3a9e8442af0c61ea63a6a688e30777396471edc221e21671196cd0f9d8d1ea0ca3c970d7cbc45dae1ba4e82c9f0b4199882ace03d432167f521");
    setReliability(3);
    dispatch({
        type:MYPAGE_USER,
        payload:["닉네임", "프로필url", "3(신뢰도)"]
    })
    dispatch({
        type:MYPAGE_TRANSACTIONS,
        payload:[{transaction_id:1, content:"나의 거래 내용", written_date:"22-02-15", nickname:"현정", reliability:3, is_mine: true, status:"진행중"}]
    })
    dispatch({
        type:MYPAGE_COMMENTS,
        payload:[{comment_id:1, comment_content:"댓글 내용", written_date:"22-02-01"}]
    })
    dispatch({
        type:MYPAGE_EVENTS,
        payload:[{event_id:1, event_title:"이벤트 제목", thumbnail_url:"썸네일url"}, {event_id:2, event_title:"이벤트 제목2", thumbnail_url:"썸네일url2"}, {event_id:3, event_title:"이벤트 제목3", thumbnail_url:"썸네일url3"}]
    })
    dispatch({
        type:MYPAGE_POSTS,
        payload:[{post_id:1, title:"제목", written_date:"22-02-14", category:"정보공유", content:"안녕하세요"}]
    })
    dispatch({
        type:MYPAGE_LIKE_TRANSACTIONS,
        payload:[{transaction_id:1, content:"거래 내용", written_date:"22-02-15", nickname:"민지", reliability:2, is_mine: false, status:"진행중"},
        {transaction_id:2, content:"거래 내용2", written_date:"22-02-16", nickname:"다은", reliability:5, is_mine: false, status:"마감"}]
    })

    }, [])

    //메뉴 선택
    const myListClick = (index, e) => {
        setMyMenu(index)
    }

    //프로필 변경 버튼 눌렀을 때
    const profileChangeClick = () => {
        setHide(false);
    }

    //프로필 변경 취소 버튼 눌렀을 때
    const profileCancelClick = () => {
        setHide(true);
    }

    //프로필 변경 완료 버튼 눌렀을 때
    const profileCompleteClick = () => {
        console.log(imgFile);
        setHide(true);
        const fd = new FormData();
        fd.append("file", imgFile[0])

        axios.patch('http://localhost:8080/mypage/profile', {
            profile_image: fd,
            user_id:"1",
        }, {
            headers: {
                "Content-Type": `multipart/form-data; `,
            }
            })
        .then((response) => {
        if(response.data){
            console.log(response.data)
        }
        })
        .catch((error) => {
            console.log(error);
        })
    }

    const handleChangeFile = (e) => {
        console.log(e.target.files)
        setImgFile(e.target.files);
        //fd.append("file", event.target.files)
        // setImgFile64([]);
        // for(var i=0;i<e.target.files.length;i++){
        //     if (e.target.files[i]) {
        //         let reader = new FileReader();
        //         reader.readAsDataURL(e.target.files[i]); // 1. 파일을 읽어 버퍼에 저장합니다.
        //         // 파일 상태 업데이트
        //         reader.onloadend = () => {
        //             // 2. 읽기가 완료되면 아래코드가 실행됩니다.
        //             const base64 = reader.result;
        //             console.log(base64)
        //             if (base64) {
        //                 //  images.push(base64.toString())
        //                 var base64Sub = base64.toString()
                        
        //                 setImgFile64(imgFile64 => [...imgFile64, base64Sub]);
        //                 //  setImgBase64(newObj);
        //                 // 파일 base64 상태 업데이트
        //                 //  console.log(images)
        //             }
        //         }
        //     }
        // }
    }

    useEffect(() => {
        preview();
        return () => preview();
    })

    const preview = () => {
        if(!imgFile) return false;

        const imgEl = document.querySelector('.preview');
        const reader = new FileReader();
        reader.onloadend = () => (
            imgEl.style.backgroundImage = `url(${reader.result})`
        )
        reader.readAsDataURL(imgFile[0]);
    }
    const nicknameChange = (e) => {
        setNickname(e.target.value);
    }

    return (
        <>
        {hide? 
        <div>
            <img src={profileImage} width="130px" height="100px"/>
            {nickname}, {reliability}
            <button onClick={profileChangeClick}>변경</button>
        </div>
        : null}

        

        {
            !hide?
            <div>
                <div className="preview" style={{width:"100px", height:"100px"}}></div>
                <input type="file" onChange={handleChangeFile} multiple="multiple"/>
                <input type="text" placeholder="닉네임" onChange={nicknameChange} value={nickname}/>
                <button>중복 확인</button>
                <button onClick={profileCompleteClick}>완료</button>
                <button onClick={profileCancelClick}>취소</button>
            </div>

            : null
        }
        
        <div>
            {myList.map((item, index) => (
                <button key={index} onClick={(e) => myListClick(index, e)}>{item}</button>
            ))}
        </div>
        <div>
            <MyPageDetail myList={myList} myMenu={myMenu}/>
        </div>
        <div>
            <button>탈퇴하기</button>
        </div>
        </>
    )
}

export default MyPageNomal;