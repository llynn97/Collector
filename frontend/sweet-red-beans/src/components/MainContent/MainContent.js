import React, {useState} from "react";
import SignUp from "../SignUp/SignUp";
import MainPage from "../MainPage/MainPage";
import {useNavigate} from "react-router-dom";
import {Route, Routes} from "react-router-dom";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import EventPage from "../EventPage/EventPage";

const MainContenet = () => {

    return (
        <>
        <MainPage/>
        </>
    );
}

export default MainContenet;