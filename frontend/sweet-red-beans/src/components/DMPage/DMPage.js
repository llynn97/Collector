import React, { useRef, useState, useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import DMList from './DMList';
import DMDetail from './DMDetail';
import axios from 'axios';
import { useNavigate } from 'react-router';
import style from '../../css/DMPage/DMPage.module.css';
import { SELECTED_DM } from '../../actions/types';

const DMPage = () => {
  const [DMlist, setDMList] = useState([]);
  const selectedRoomId = useSelector((s) => {
    if (s === undefined) {
      return null;
    } else {
      if (s.selectedRoom !== undefined) {
        return s.selectedRoom;
      } else if (s.DMCreate !== undefined) {
        return s.DMCreate;
      } else {
        return null;
      }
    }
  });

  useEffect(() => {
    //DM 목록 조회
    axios
      .get('http://localhost:8080/direct-message', {
        withCredentials: true,
      })
      .then((response) => {
        setDMList(response.data.room_id);
      })
      .catch((error) => {});

    return () => {
      setDMList(null);
    };
  }, []);

  console.log(selectedRoomId);

  useEffect(() => {
    console.log(selectedRoomId);
  }, [selectedRoomId]);

  return (
    <>
      <div className={style.dmpage}>
        <div>
          <DMList DMlist={DMlist} />
        </div>

        <div>
          {selectedRoomId !== null ? (
            <DMDetail selectedRoom={selectedRoomId} />
          ) : null}
        </div>
      </div>
    </>
  );
};

export default DMPage;
