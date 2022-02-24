import React, {useState, useCallback, useEffect, useMemo} from "react";
import ChatPresenter from "../DMPage/ChatPresenter";
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";

const Test = () => {
    const h = "안녕"
    const data = {content:h}
    console.log(JSON.stringify(data));
    let sockJS = new SockJS("http://localhost:8080/ws-stomp");
    let stompClient = Stomp.over(sockJS);
    stompClient.debug= () => {};

    const [contents, setContents] = React.useState([]);
    const [username, setUsername] = React.useState('');
    const [message, setMessage] = React.useState("");

    useEffect(()=>{
        console.log(contents);
        stompClient.connect({},()=>{
        stompClient.subscribe('/sub/chat/room/2',(data)=>{
            console.log(JSON.parse(data.body));
            console.log("sub");
            const newMessage = JSON.parse(data.body);
            addMessage(newMessage);
        });
    });
    },[contents]);
    
    const handleEnter = (username, content) => {
        stompClient.send("/pub/chat/message",{},JSON.stringify({ 
            content : "안녕",
            user_id : 1,
            chat_room_id: 2,
            nickname: "닉네임", }));
        setMessage("");
    };

    const addMessage = (message) =>{
        setContents(prev=>[...prev, message]);
    };
    return(
        <>
        <div className={"container"}>
            <ChatPresenter
            contents={contents}
            handleEnter={handleEnter}
            message={message}
            setMessage={setMessage}
            username={username}
            setUsername={setUsername}
            />
        </div>
        </>
    )
}

export default Test;