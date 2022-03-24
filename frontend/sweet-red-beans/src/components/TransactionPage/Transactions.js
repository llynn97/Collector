import React, { useEffect, useRef, useCallback, useState } from 'react';
import { useNavigate } from 'react-router';
import TransactionDetail from './TransactionDetail';

const Transactions = ({ transactions }) => {
    const navigation = useNavigate();
    useEffect(() => {
        console.log(transactions);
    }, [transactions]);

    return (
        <>
            {transactions.map((item, index) => (
                <div key={index}>
                    <TransactionDetail transaction={item} />
                </div>
            ))}
        </>
    );
};

export default Transactions;
