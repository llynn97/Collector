import React, {useRef, useState, useEffect} from "react";
import { useSelector } from "react-redux";
import DMList from "./DMList";
import DMDetail from "./DMDetail";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';

const DMPage = () => {

    // //연결됐을 때
    // client.onConnect = function (frame) {

    // };
    


    // client.publish({
    //     destination: '/topic/general',
    //     body: 'Hello world',
    //     headers: { priority: '9' },
    // });

    //메시지 받기
    //const subscription = client.subscribe('/queue/test', callback);

    //------------------------------------------------------


    const [content, setContent] = useState("");

    //1:유저아이디

    useEffect(() => {
        wsSubscribe();
        return () => wsDisconnect();
    }, [])


    const client = new StompJs.Client({
        brokerURL: 'ws://localhost/8080/ws-stomp/websocket',
        connectHeaders: {
            login: 'user',
            passcode: 'password',
        },
        debug: function (str) {
            console.log(str);
        },
        reconnectDelay: 5000, //자동 재 연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

    client.onConnect = () => {
        console.log("클라이언트 연결");
    }

    client.onStompError = () => {
        console.log("클라이언트 에러");
    }

    client.activate();
    // //에러났을 때
    // client.onStompError = function (frame) {
    //     console.log('Broker reported error: ' + frame.headers['message']);
    //     console.log('Additional details: ' + frame.body);
    // };

    const onClick = (message) => {
        console.log(message);
        if(!client.connected)
            return;
        
        //메시지 보내기
        client.publish({
            destination: 'http://localhost/8080/chat/message',
            //destination: '/topic/general',
            body: JSON.stringify({
                content:message
            })
        })
    }

    const wsSubscribe = () => {
        //메시지 받기
        //1 : 룸 id
        // client.subscribe('/sub/chat/room/'+"1", (msg) => {
        //     const newMessage = JSON.parse(msg.body).message;
        //     setContent(newMessage);
        // }, {id: "user"})
    }

    const wsDisconnect = () => {
        //클라이언트 비활성화
        client.deactivate();
    }

    return(
        <>
        <div>
            {content}
        </div>
        <DMList/>
        <DMDetail sendMessage={onClick}/>
        </>
    );
}

export default DMPage;