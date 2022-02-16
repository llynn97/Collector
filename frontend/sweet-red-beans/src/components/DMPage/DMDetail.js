import React, {useState, useCallback, useEffect, useMemo} from "react";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";
import Modal from "../../components/Modals/ReportModal";

let stompClient = null;

//props를 selectedRoom으로 바꾸고 roomId는 selectedRoom.chat_room_id으로 바꾸기
//transaction_id 값 바꾸기
const DMDetail = ({roomId}) => {
    const [ms, setMs] = useState("");
    const [mList, setMList] = useState([]);
    const [detailMessages, setDetailMessages] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [reportContent, setReportContent] = useState("");
    //거래완료되면 true
    //초기값 selectedRoom.is_complete로 설정하기
    const [complete, setComplete] = useState(false);

    const openModal = () => {
        setModalOpen(true);
    };
    const closeModal = () => {
        setModalOpen(false);
    };

    let socket = new WebSocket('ws://localhost:8080/ws-stomp');
    
    //서버와 연결됐을 때
    socket.onopen = (e) => {
        stompClient = Stomp.over(socket);
        console.log("open server!!!!");

        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            //입장에 대한 구독
            stompClient.subscribe('/sub/chat/room/' + "1", function(response) {
                console.log(response);
            });
            //메시지 전달 구독
            // stompClient.subscribe('/sub/chat/message', function(response) {
            //     console.log(response);
            // });
            
        })
    }

    //에러 발생했을 때
    socket.onerror = () => {

    }

    socket.onmessage = (e) => {
        console.log(e.data);
    }

    const sendMessage = (message) => {
        stompClient.send("/pub/chat/message", {}, JSON.stringify({
            content : message,
            user_id : "1",
            chat_room_id: roomId,
            nickname: "민지",
        }))

        // console.log(message);
        // if(!client.connected)
        //     return;
        
        // //메시지 보내기
        // client.publish({
        //     destination: 'http://localhost/8080/chat/message',
        //     //destination: '/topic/general',
        //     body: JSON.stringify({
        //         content:message
        //     })
        // })
    }

    
    const onChange = useCallback(
        (e) => {
            setMs(e.target.value);
        }, []
    )

    const sendClick = () => {
        sendMessage(ms);
        const content = {
            message_content:ms,
            nickname:"aaa",
            written_date:new Date(),
        }
        setMList([...mList, content]);
        setMs("");
    }

    const disconnect = () => {
        if(stompClient != null) {
            stompClient.disconnect();
        }
    }

    //이제까지 메시지 내역 조회
    useEffect(() => {
        axios.get("http://localhost:8080/direct-message/detail", {
            params:{
                room_id: roomId
            }
        })
        .then(response => {
            console.log(response.data);
            //setMList(response.data.message.map(item => item.message_content));
            setMList(response.data.message);
        })
        .catch(error => console.log(error))
        
    }, [roomId])

    //신뢰도 +1 주는 버튼
    const reliabilityPlusClick = () => {
        //신뢰도 1 주기

        // const body = {
        //     user_id: selectedRoom.not_mine_id
        // }
        // axios.post('http://localhost:8080/direct-message/reliability', body)
        // .then(response => {
        //     if(response.data.result){
        //         alert("상대방의 신뢰도가 올랐습니다.")
        //     }
        //     else {
        //         alert("신뢰도를 올리는 데 실패했습니다.")
        //     }
        // })
        // .catch(error => console.log(error));
    }

    const reportContentChange = (e) => {
        setReportContent(e.target.value);
    }

    //신고 확인 버튼 눌렀을 때
    const reportConfirm = () => {
        const body = {
            user_id : "1",
            transaction_id : "1",
            report_content : reportContent,
        }
        axios.post('http://localhost:8080/direct-message/report', body)
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
        .catch(error => console.log(error));
    }

    //삭제 취소 버튼 눌렀을 때
    const cancelConfirm = (e) => {
        console.log("신고 취소");
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

    //거래완료 버튼 눌렀을 때
    const completeClick = () => {
        const body = {
            transaction_id: "1",
        }
        axios.post('http://localhost:8080/direct-message/transaction-complete', body)
        .then(response => {
            if(response.data.result){
                setComplete(true);
            }
            else {
                alert("거래완료에 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
        
    }

    return (
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
        <h2>DM 메시지 창</h2>
        <div style={{width:"200px", height:"300px"}}>
            {roomId}, 메시지 내용 올라오는 곳
            <div>
                닉네임, 신뢰도 : 0
                <button onClick={reliabilityPlusClick}>신뢰도 주기</button>
                <button onClick={openModal}>신고하기</button>
            </div>
            {mList.map((item, index) => <div key={index}>{item.nickname} : {item.message_content}</div>)}
            {
            //detailMessages.map((item, index) => <div key={index}>{item.nickname}</div>)
            }
        </div>
        {complete ? <div>거래가 완료되었습니다.</div> : null}
        <input type="text" value={ms} onChange={onChange} name={ms}/>
        <button onClick={sendClick}>전송</button>
        <button onClick={completeClick}>거래완료</button>
        </>
    );
}

export default DMDetail;