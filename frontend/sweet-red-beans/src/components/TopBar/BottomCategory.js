import React from "react";
import { Link } from "react-router-dom";
import style from "../../css/TopBar/BottomCategory.module.css";

const BottomCategory = () => {

    return(
        <>
        <div>
            <button className={style.noneBottomCategory}>하위 카테고리</button>
            <Link to = {`/informationShare`}>
                <button className={style.bottomCategory}>정보 공유</button>
            </Link>
            <button className={style.noneBottomCategory}>하위 카테고리</button>
            <button className={style.noneBottomCategory}>하위 카테고리</button>
        </div>
        </>
    );
}

export default BottomCategory;