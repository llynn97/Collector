import React, { useReducer, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import styles from "../../css/NavigationBar.module.css";
import BottomCategory from "./BottomCategory";
import { useNavigate } from "react-router";

const NavigationBar = () => {
    const navigation = useNavigate();
    const [hide, setHide] = useState(true);

    const onMouseEnter = () => {
        setHide(false);
    }

    const onMouseLeave = () => {
        setHide(true);
    }

    const eventClick = () => {
        navigation('/event');
        navigation(0);
    }

    const transactionClick = () => {
        navigation('/transaction');
        navigation(0);
    }

    const imformationShareClick = () => {
        navigation('/informationShare');
        navigation(0);
    }


    return (
        <>
        <div onMouseEnter={onMouseEnter} onMouseLeave={onMouseLeave}>
            <div className={styles.topBar}>
                <Link to = {`/event`}>
                    <button className={styles.topBarButton} onClick={eventClick}>이벤트</button>
                </Link>
                <Link to={`/transaction`}>
                    <button className={styles.topBarButton} onClick={transactionClick}>대리구매</button>
                </Link>
                <Link to = {`/informationShare`}>
                    <button className={styles.topBarButton} onClick={imformationShareClick}>커뮤니티</button>
                </Link>
                <button className={styles.topBarButton}>개인메시지</button>
                <button className={styles.topBarButton}>마이페이지</button>
            </div>
            <div>
                {!hide ? <BottomCategory/> : null}
            </div>
        </div>
        
        </>
    );
}

export default NavigationBar;