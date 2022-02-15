import React, {useRef, useState, useEffect, useMemo} from "react";
import { useSelector } from "react-redux";
import DMList from "./DMList";
import DMDetail from "./DMDetail";
import * as StompJs from '@stomp/stompjs';
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
import Stomp from "stompjs";
import axios from "axios";
import store from "../../store";

const DMPage = () => {
    const selectedRoomId = useSelector(s => {
        if(s !== undefined) {
            return s.selectedRoomId
        }
        else {
            return "none"
        }
    })

    useEffect(() => {
        console.log(selectedRoomId);
    }, [selectedRoomId])

    return(
        <>
        <div>
            <DMList/>
        </div>
        {useMemo(() => (selectedRoomId !== "none" ? <DMDetail roomId={selectedRoomId}/> : null), [selectedRoomId])}
        </>
    );
}

export default DMPage;