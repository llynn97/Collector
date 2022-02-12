import React, {useState, useCallback} from "react";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";

let stompClient = null;

const DMDetail = () => {
    const [ms, setMs] = useState("");
    const [mList, setMList] = useState([]);
    const socket = new SockJS('http://localhost:8080/ws-stomp');
    stompClient = Stomp.over(socket);
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


    const sendMessage = (message) => {
        stompClient.send("/pub/chat/message", {}, JSON.stringify({
            content : message,
            user_id : "1",
            chat_room_id: "4",
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

    const onClick = () => {
        sendMessage(ms);
        setMList([...mList, ms]);
        setMs("");
    }

    const disconnect = () => {
        if(stompClient != null) {
            stompClient.disconnect();
        }
    }
    return (
        <>
        <h2>DM 메시지 창</h2>
        <div style={{width:"200px", height:"300px"}}>
            메시지 내용 올라오는 곳
            {mList.map((item, index) => <div key={index}>{item}</div>)}
        </div>
        <input type="text" value={ms} onChange={onChange} name={ms}/>
        <button onClick={onClick}>전송</button>
        </>
    );
}

export default DMDetail;