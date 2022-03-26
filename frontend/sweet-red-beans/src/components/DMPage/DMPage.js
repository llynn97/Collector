import React, { useRef, useState, useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import DMList from './DMList';
import DMDetail from './DMDetail';
import axios from 'axios';
import { useNavigate } from 'react-router';
import style from '../../css/DMPage/DMPage.module.css';
import { SELECTED_DM } from '../../actions/types';

const DMPage = () => {
  const selectedRoomId = useSelector((s) => {
    if (s === undefined) {
      return null;
    } else {
      return s.selectedRoom;
    }
  });

  return (
    <>
      <div className={style.dmpage}>
        <div>
          <DMList />
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
