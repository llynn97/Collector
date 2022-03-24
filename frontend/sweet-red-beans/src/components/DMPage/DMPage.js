import React, { useRef, useState, useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import DMList from './DMList';
import DMDetail from './DMDetail';
import * as StompJs from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import SockJsClient from 'react-stomp';
import Stomp from 'stompjs';
import axios from 'axios';
import store from '../../store';
import { useNavigate } from 'react-router';
import style from '../../css/DMPage/DMPage.module.css';
import { SELECTED_DM } from '../../actions/types';

const DMPage = () => {
    const dispatch = useDispatch();
    const [DMlist, setDMList] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState(null);

    const navigation = useNavigate();
    const [renderError, setRenderError] = useState(true);

    useEffect(() => {
        //DM 목록 조회
        axios
            .get('http://localhost:8080/direct-message', {
                withCredentials: true,
            })
            .then((response) => {
                setDMList(response.data.room_id);
                setRenderError(false);
            })
            .catch((error) => {
                setRenderError(true);
                alert('로그인을 먼저 해주세요');
                navigation('/');
            });

        return () => {
            dispatch({
                type: SELECTED_DM,
                payload: null,
            });
        };
    }, []);

    const DMListClick = (selectedRoom, e) => {
        dispatch({
            type: SELECTED_DM,
            payload: selectedRoom.chat_room_id,
        });
        setSelectedRoomId(selectedRoom);
    };

    return (
        <>
            {!renderError ? (
                <>
                    <div className={style.dmpage}>
                        <div>
                            <DMList DMlist={DMlist} DMListClick={DMListClick} />
                        </div>
                        <div>
                            {selectedRoomId !== null ? (
                                <DMDetail selectedRoom={selectedRoomId} />
                            ) : null}
                        </div>
                    </div>
                </>
            ) : null}
        </>
    );
};

export default DMPage;
