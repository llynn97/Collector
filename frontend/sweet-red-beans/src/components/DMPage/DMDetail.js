import React, {useState, useCallback, useEffect} from "react";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";

let stompClient = null;

const DMDetail = ({roomId}) => {
    const [ms, setMs] = useState("");
    const [mList, setMList] = useState([]);

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
        setMList([...mList, ms]);
        setMs("");
    }

    const disconnect = () => {
        if(stompClient != null) {
            stompClient.disconnect();
        }
    }

    useEffect(() => {
        axios.get("http://localhost:8080/direct-message/detail", {
            params:{
                room_id: roomId
            }
        })
        .then(response => {
            setMList(response.data.message.map(item => item.message_content));
        })
        .catch(error => console.log(error))
    }, [roomId])

    return (
        <>
        <h2>DM 메시지 창</h2>
        <div style={{width:"200px", height:"300px"}}>
            {roomId}, 메시지 내용 올라오는 곳
            {mList.map((item, index) => <div key={index}>{item}</div>)}
        </div>
        <input type="text" value={ms} onChange={onChange} name={ms}/>
        <button onClick={sendClick}>전송</button>
        </>
    );
}

export default DMDetail;