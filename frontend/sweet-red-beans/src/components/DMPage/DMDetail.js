import React, {useState, useCallback, useEffect, useMemo, useRef} from "react";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";
import Modal from "../../components/Modals/ReportModal";

let stompClient = null;

//props를 selectedRoom으로 바꾸고 roomId는 selectedRoom.chat_room_id으로 바꾸기
//transaction_id 값 바꾸기
const DMDetail = ({selectedRoom}) => {
    const [ms, setMs] = useState("");
    const [mList, setMList] = useState([]);
    const [detailMessages, setDetailMessages] = useState([]);

    const [modalOpen, setModalOpen] = useState(false);

    const [reportContent, setReportContent] = useState("");
    //거래완료되면 true
    //초기값 selectedRoom.is_complete로 설정하기
    const [complete, setComplete] = useState(false);

    const [imgFile, setImgFile] = useState(null);

    const openModal = () => {
        setModalOpen(true);
    };
    const closeModal = () => {
        setModalOpen(false);
    };


    //대화 내용, 상대방 것 포함
    const [contents, setContents] = useState([]);
    const [username, setUsername] = useState("");
    //내가 보낸 메시지 내용
    const [message, setMessage] = useState("");

    let socket = new SockJS('http://localhost:8080/ws-stomp');
    let stompClient = Stomp.over(socket);
    
    //서버와 연결됐을 때
    useEffect(() => {
        console.log(socket);

    }, [])

    useEffect(() => {
        
    }, [contents])


    //에러 발생했을 때
    socket.onerror = (e) => {
        console.log(e);
    }

    socket.onmessage = (e) => {
        console.log(e);
    }
    
    //전송 버튼 눌렀을 때
    const sendClick = () => {
        sendMessage(message);
        //서버에서 받아올 때처럼 비슷한 형식으로 넣어주기 위해
        setMessage("");
    }

    const sendMessage = (message) => {
        const fd = new FormData();
        
        if(imgFile === null){
            if(message !== ""){
                fd.append("content", message);
                fd.append("chat_room_id", selectedRoom.chat_room_id);
                let obj = {}
                fd.forEach((v, k) => obj[k]=v);
                console.log(obj);
                stompClient.send("/pub/chat/message", {}, JSON.stringify({
                    content : message,
                    chat_room_id: selectedRoom.chat_room_id,
                }));
            }

        } else {
            fd.append("image_url", imgFile[0]);
            fd.append("content", message);
            fd.append("chat_room_id", selectedRoom.chat_room_id);
            let obj = {}
            fd.forEach((v, k) => obj[k]=v);
            console.log(obj);
            stompClient.send("/pub/chat/message", {}, JSON.stringify(obj));
            setImgFile(null);
        }
        
    }

    const addMessage = (message) =>{
        //상대에게 받아온 메시지를 추가함
        setContents(prev=>[...prev, message]);
    };

    
    const onChange = useCallback(
        (e) => {
            setMessage(e.target.value);
        }, []
    )


    const disconnect = () => {
        if(stompClient != null) {
            stompClient.disconnect();
        }
    }

    //이제까지 메시지 내역 조회
    useEffect(() => {
        if(selectedRoom !== undefined){
            axios.get("http://localhost:8080/direct-message/detail", {
                withCredentials: true,
                params:{
                    room_id: selectedRoom.chat_room_id,
                }
            })
            .then(response => {
                //setMList(response.data.message.map(item => item.message_content));
                setContents(response.data.message);
            })
            .catch(error => console.log(error))
        }

        stompClient.connect({}, () => {
            stompClient.subscribe('/sub/chat/room/' + selectedRoom.chat_room_id, (data) => {
                console.log(JSON.parse(data.body));
                const newMessage = JSON.parse(data.body);
                addMessage(newMessage);
            })
        });
    }, [selectedRoom])

    //신뢰도 +1 주는 버튼
    const reliabilityPlusClick = () => {
        //신뢰도 1 주기

        const body = {
            user_id: selectedRoom.not_mine_id
        }
        axios.post('http://localhost:8080/direct-message/reliability', body, { withCredentials: true })
        .then(response => {
            if(response.data.result){
                alert("상대방의 신뢰도가 올랐습니다.")
            }
            else {
                alert("신뢰도를 올리는 데 실패했습니다.")
            }
        })
        .catch(error => console.log(error));
    }

    //신고 내용
    const reportContentChange = (e) => {
        setReportContent(e.target.value);
    }

    //신고 확인 버튼 눌렀을 때
    const reportConfirm = () => {
        const body = {
            user_id : "1",
            transaction_id : selectedRoom.transaction_id,
            report_content : reportContent,
        }
        axios.post('http://localhost:8080/direct-message/report', body, { withCredentials: true })
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
            transaction_id: selectedRoom.transaction_id,
        }
        axios.post('http://localhost:8080/direct-message/transaction-complete', body, { withCredentials: true })
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

    useEffect(() => {
        let messagebox = document.querySelector("#messagebox");
        messagebox.scrollTop = messagebox.scrollHeight;
    }, [contents])

    const handleChangeFile = (e) => {
        console.log(e.target.files)
        setImgFile(e.target.files);
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

    useEffect(() => {
        if(imgFile !== null){
            console.log(imgFile[0]);
        }

    }, [imgFile])

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
        {useMemo(() => 
        <div style={{width:"200px", height:"300px"}}>
            {selectedRoom !== undefined ? selectedRoom.chat_room_id : null}, 
            메시지 내용 올라오는 곳
            <div>
                닉네임, 신뢰도 : 0
                <button onClick={reliabilityPlusClick}>신뢰도 주기</button>
                <button onClick={openModal}>신고하기</button>
            </div>
            <div style={{width:"200px", height:"200px", overflow:"auto"}} id="messagebox">
                {
                //mList.map((item, index) => <div key={index}>{item.nickname} : {item.message_content}</div>)
            }
            {contents.map((message, index) => (
                <div key={index}> {message.nickname} : {message.content} </div>
            ))}
            </div>
            
            
            {
            //detailMessages.map((item, index) => <div key={index}>{item.nickname}</div>)
            }
        </div>
        
        , [contents])}

        <div className="preview" style={{width:"100px", height:"100px"}}></div>
        {complete ? <div>거래가 완료되었습니다.</div> : null}
        <input type="text" value={message} onChange={onChange} name={message}/>
        <button onClick={sendClick}>전송</button>
        <label for="upload_file">업로드</label>
        <input type="file" onChange={handleChangeFile} id="upload_file" style={{display:"none"}}/>
        <button onClick={completeClick}>거래완료</button>
        </>
    );
}

export default DMDetail;