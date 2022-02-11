import React, {useState} from "react";
import SignUp from "../SignUp/SignUp";
import MainPage from "../MainPage/MainPage";
import {useNavigate} from "react-router-dom";
import {Route, Routes} from "react-router-dom";
import EventDetailPage from "../EventDetailPage/EventDetailPage";
import EventPage from "../EventPage/EventPage";
import { useDispatch, useSelector } from "react-redux";
import { LOGIN_USER } from "../../actions/types";
import TopBar from "../TopBar/TopBar";
import InformationSharePage from "../InformationShares/InformationSharePage";
import InformationShareWritePage from "../InformationShares/InformationShareWritePage";
import InformationShareDetailPage from "../InformationShares/InformationShareDetailPage";
import TransactionPage from "../TransactionPage/TransactionPage";
import DMPage from "../DMPage/DMPage";



const MainContenet = () => {

    return (
        <>
        <TopBar/>
        <Routes>
          <Route path="/" element={<MainPage/>}/>
          <Route path="/signup" element={<SignUp/>}/>
          <Route path="/event/*" element={<EventPage/>}/>
          <Route path="/event/:id" element={<EventDetailPage/>}/>
          <Route path="/informationShare/*" element={<InformationSharePage/>}/>
          <Route path="/informationShareWrite" element={<InformationShareWritePage/>}/>
          <Route path="/informationShare/:postid" element={<InformationShareDetailPage/>}/>
          <Route path="/transaction/*" element={<TransactionPage/>}/>
          <Route path="/DM" element={<DMPage/>}/>
        </Routes>
        </>
    );
}

export default MainContenet;