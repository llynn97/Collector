import React from "react";
import SignUp from "../SignUp/SignUp";
import MainPage from "../MainPage/MainPage";
import {Route, Routes} from "react-router-dom";
import EventDetailPage from "../EventPage/EventDetailPage";
import EventPage from "../EventPage/EventPage";
import TopBar from "../TopBar/TopBar";
import InformationSharePage from "../InformationShares/InformationSharePage";
import InformationShareWritePage from "../InformationShares/InformationShareWritePage";
import InformationShareDetailPage from "../InformationShares/InformationShareDetailPage";
import TransactionPage from "../TransactionPage/TransactionPage";
import DMPage from "../DMPage/DMPage";
import MyPageAdmin from "../MyPage/MyPageAdmin";
import MyPageNormal from "../MyPage/MyPageNormal";
import KakaoLogin from "../TopBar/KakaoLogin";
import GoogleLogin from "../TopBar/GoogleLogin";
import MyEvents from "../MyPage/MyPageDetail/MyEvents";
import MyTransactions from "../MyPage/MyPageDetail/MyTransactions";
import MyLikeTransactions from "../MyPage/MyPageDetail/MyLikeTransactions";
import MyPosts from "../MyPage/MyPageDetail/MyPosts";
import MyComments from "../MyPage/MyPageDetail/MyComments";

const MainContent = () => {
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
          <Route path="/mypage" element={<MyPageNormal/>}>
            <Route path="myevents" element={<MyEvents/>}/>
            <Route path="mytransactions" element={<MyTransactions/>}/>
            <Route path="myliketransactions" element={<MyLikeTransactions/>}/>
            <Route path="myposts" element={<MyPosts/>}/>
            <Route path="mycomments" element={<MyComments/>}/>
          </Route>
          <Route path="/adminpage" element={<MyPageAdmin/>}/>
          <Route path="/signin/oauth2/code/kakao" element={<KakaoLogin/>}/>
          <Route path="/signin/auth/google/callback" element={<GoogleLogin/>}/>
        </Routes>
        </>
    );
}

export default MainContent;