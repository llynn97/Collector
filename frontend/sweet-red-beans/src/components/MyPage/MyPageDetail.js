import React from "react";
import MyEvents from "./MyPageDetail/MyEvents";
import MyTransactions from "./MyPageDetail/MyTransactions";
import MyLikeTransactions from "./MyPageDetail/MyLikeTransactions";
import MyPosts from "./MyPageDetail/MyPosts";
import MyComments from "./MyPageDetail/MyComments";

const MyPageDetail = ({myList, myMenu}) => {
    const myComponents = [<MyEvents/>, <MyTransactions/>, <MyLikeTransactions/>, <MyPosts/>, <MyComments/>]
    
    return (
        <>
        {myComponents[myMenu]}
        </>
    )
}

export default MyPageDetail;