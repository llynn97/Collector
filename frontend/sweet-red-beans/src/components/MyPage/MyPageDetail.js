import React, { useEffect } from 'react';
import MyEvents from './MyPageDetail/MyEvents';
import MyTransactions from './MyPageDetail/MyTransactions';
import MyLikeTransactions from './MyPageDetail/MyLikeTransactions';
import MyPosts from './MyPageDetail/MyPosts';
import MyComments from './MyPageDetail/MyComments';
import { Outlet, useParams } from 'react-router';
import { Route, Routes } from 'react-router-dom';

const MyPageDetail = ({ myMenu }) => {
    const { id } = useParams();
    const myComponents = [
        <MyEvents />,
        <MyTransactions />,
        <MyLikeTransactions />,
        <MyPosts />,
        <MyComments />,
    ];

    useEffect(() => {
        console.log(id);
    }, [id]);
    return <>{/* {myComponents[myMenu]} */}</>;
};

export default MyPageDetail;
