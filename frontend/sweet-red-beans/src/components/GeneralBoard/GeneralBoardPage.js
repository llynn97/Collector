import React, { useEffect, useState, useMemo, useCallback } from "react";
import { useSelector, useDispatch } from "react-redux";
import { CGVarea, CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8 } from "../../cinemas/CGVcinemas";
import { LCarea, LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7 } from "../../cinemas/LCcinemas";
import { MBarea, MB0, MB1, MB2, MB3, MB4, MB5, MB6 } from "../../cinemas/MBcinemas";
import { CQarea, CQ0, CQ1, CQ2, CQ3, CQ4 } from "../../cinemas/CQcinemas";
import GeneralBoard from "./GeneralBoard";
import { Link } from "react-router-dom";
import { INFO } from "../../actions/types";
import axios from "axios";
import style from "../../css/GeneralBoardPage/GeneralBoardPage.module.css";

const GeneralBoardPage = () => {
    const dispatch = useDispatch();

    const [array, setArray] = useState([]);
 
    //이제까지 검색한 것
    const [searchWords, setSearchWords] = useState([]);
    
    //search
    const [search, setSearch] = useState("");

    const sorts = ["제목+내용", "제목", "내용", "작성자"];
    const [sort, setSort] = useState("제목+내용");

    useEffect(() => {
        document.documentElement.scrollTop = 0;
    }, [])


    const sortChange = (e) => {
        setSort(e.target.value);
    }

    const searchChange = useCallback((e) => {
        setSearch(e.target.value);
    },[search]);

    //검색 버튼 클릭했을 때
    const searchClick = () => {
        setSearchWords([...searchWords, search]);
    }

    return(
        <>
        <div className={style.whiteBox}>
            <div className={style.filterArea}>
                <div className={style.filter}>
                    <select onChange={sortChange} value={sort}>
                        {sorts.map((item) => (
                            <option value={item} key={item}>{item}</option>
                        ))}
                    </select>
                    <span className={style.filterArrow}></span>
                </div>

                <div className={style.search}>
                    <input type="text" placeholder="검색" onChange={searchChange} value={search} size="5"></input>
                    <div className={style.underline}></div>
                    <button id="searchButton" onClick={searchClick}></button>
                </div>
            </div>
            <GeneralBoard
            searchWords={searchWords} 
            sort={sort}/>

        </div>
        
        </>
    );
}

export default GeneralBoardPage;