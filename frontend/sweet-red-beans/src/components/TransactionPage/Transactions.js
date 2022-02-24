import React, { useEffect, useRef, useCallback, useState } from "react";
import TransactionDetail from "./TransactionDetail";

const Transactions = ({transactions}) => {
    useEffect(()=>{
        console.log(transactions);
    }, [transactions])
    
    return(
        <>

        {
        transactions.map((item, index) => <div key={index}><TransactionDetail transaction={item}/></div>)
        }
        </>
    );
}

export default Transactions;