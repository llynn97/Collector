import React, { Fragment, useEffect, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { MAIN_CINEMA_EVENTS } from '../../actions/types';
import { Route, Routes } from 'react-router';
import MainPage from './MainPage';
import axios from 'axios';
import style from '../../css/MainPage/MovieThumbnail.module.css';

//props랑 {cinemaName}이 똑같아야 함
const Test = ({ thisEvent }) => {
    //좋아요 상태 변경용

    return <div>{thisEvent.title}</div>;
};

export default Test;
