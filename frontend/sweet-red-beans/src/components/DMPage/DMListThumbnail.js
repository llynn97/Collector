import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import style from "../../css/DMPage/DMListThumbnail.module.css";
import { parseDate } from "../../parseDate/parseDate";

const DMListThumbnail = ({dm}) => {
    const [currentRoom, setCurrentRoom] = useState(false);
    const selectedRoomId = useSelector(s => {
        if (s === undefined) {
            return null;
        }
        else {
            return s.selectedRoomId;
        }
    })

    useEffect(() => {
        if(selectedRoomId !== null) {
            if (dm.chat_room_id === selectedRoomId) {
                console.log("a의 번호: ", selectedRoomId);
                console.log("같음 : ", dm.chat_room_id);
                setCurrentRoom(true);
            }
            else {
                setCurrentRoom(false);
            }
        }
    }, [selectedRoomId])
    
    return (
        <>
        <div className={currentRoom ? style.currentThumbnail : style.thumbnail}>
            <img src={dm.not_mine_profile_url}/>
            <div className={style.contentArea}>
                <div>{dm.not_mine_nickname}</div>
                <div>{dm.recent_message}</div>
            </div>
            <div>
                {parseDate(dm.recent_message_date)}
            </div>
        </div>
        
        </>
    )
}

export default DMListThumbnail