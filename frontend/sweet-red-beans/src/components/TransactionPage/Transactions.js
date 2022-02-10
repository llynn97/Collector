import React, { useEffect } from "react";
import TransactionDetail from "./TransactionDetail";

const Transactions = ({transactions}) => {
    useEffect(()=>{
        console.log(transactions);
    }, [transactions])
    return(
        <>
        <h3>거래 목록</h3>
        {transactions.map((item, index) => <div key={index}><TransactionDetail transaction={item}/></div>)
        }
        </>
    );
}

export default Transactions;