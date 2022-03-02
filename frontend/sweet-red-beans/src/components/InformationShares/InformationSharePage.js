import React, { useEffect, useState, useMemo, useCallback } from "react";
import { useSelector, useDispatch } from "react-redux";
import { CGVarea, CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8 } from "../../cinemas/CGVcinemas";
import { LCarea, LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7 } from "../../cinemas/LCcinemas";
import { MBarea, MB0, MB1, MB2, MB3, MB4, MB5, MB6 } from "../../cinemas/MBcinemas";
import { CQarea, CQ0, CQ1, CQ2, CQ3, CQ4 } from "../../cinemas/CQcinemas";
import InformationShares from "./InformationShares";
import { Link } from "react-router-dom";
import { INFO } from "../../actions/types";
import axios from "axios";

const a = () => {
    console.log("안녕");
}

const InformationSharePage = () => {
    const dispatch = useDispatch();

    const cinemaNames = ["전체", "CGV", "롯데시네마", "메가박스", "씨네큐"];
    const CGVarray = [CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8];
    const LCarray = [LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7];
    const MBarray = [MB0, MB1, MB2, MB3, MB4, MB5, MB6];
    const CQarray = [CQ0, CQ1, CQ2, CQ3, CQ4];

    //필터에서 선택한 영화사, 지역, 지점 이름
    const [cinemaName, setCinemaName] = useState("전체");
    const [cinemaArea, setCinemaArea] = useState("지역");
    const [cinemaBranch, setCinemaBranch] = useState("지점");
    //필터에서 선택하면 true로 바뀜
    const [cinemaNameSelected, setCinemaNameSelected] = useState(false);
    const [cinemaAreaSelected, setCinemaAreaSelected] = useState(false);
    const [cinemaBranchSelected, setCinemaBranchSelected] = useState(false); 
    //보여줄 array
    const [cinemaAreas, setCinemaAreas] = useState([]);
    const [array, setArray] = useState([]);
    const [cinemaBranches, setCinemaBranches] = useState([]);
    //이제까지 검색한 것
    const [searchWords, setSearchWords] = useState([]);
    
    //search
    const [search, setSearch] = useState("");

    const sorts = ["제목+내용", "제목", "내용", "작성자"];
    const [sort, setSort] = useState("제목+내용");

    //게시글들
    const [infos, setInfos] = useState([]);
    const [infoIsHere, setInfoIsHere] = useState(false);

    const serverReq = () => {
        axios.get('http://localhost:8080/information-share/search',{
        withCredentials: true,
        params: {
                    search_word : search,
                    cinema_name : cinemaName,
                    cinema_area : cinemaArea,
                    cinema_branch : cinemaBranch,
                    sort_name: sort,
                }
        })
        .then(response => setInfos(response.data))
        .catch(error => console.log(error));
    }

    //영화관을 선택했을 때
    const cinemaNameChange = (e) => {
        if(e.target.value === "전체"){
            setCinemaName("전체");
            setCinemaNameSelected(false);
            setCinemaAreaSelected(false);
            setCinemaBranchSelected(false);
        }
        else {
            const selected = e.target.value;
            setCinemaName(selected);
            if(selected === "CGV"){
                setCinemaAreas(CGVarea);
                setArray(CGVarray);
            } else if (selected === "롯데시네마"){
                setCinemaAreas(LCarea);
                setArray(LCarray);
            } else if (selected === "메가박스"){
                setCinemaAreas(MBarea);
                setArray(MBarray);
            } else if (selected === "씨네큐"){
                setCinemaAreas(CQarea);
                setArray(CQarray);
            }
            setCinemaNameSelected(true);
        }
    }

    //지역을 선택했을 때
    const cinemaAreaChange = (e) => {
        if(e.target.value === "지역"){
            setCinemaArea("지역")
            setCinemaAreaSelected(false);
            setCinemaBranchSelected(false);
        }
        else {
            setCinemaArea(e.target.value);
            setCinemaAreaSelected(true);
            //선택한 지역에 따라 해당 지역에 있는 지점들 리스트로 지정해줌
            setCinemaBranches(array[cinemaAreas.indexOf(e.target.value)-1]);
        }
    }

    //지점 선택했을 때
    const cinemaBranchChange = (e) => {
        setCinemaBranch(e.target.value);
        setCinemaBranchSelected(true);
    }

    const sortChange = (e) => {
        setSort(e.target.value);
    }

    const searchChange = useCallback((e) => {
        setSearch(e.target.value);
    },[search]);

    //검색 버튼 클릭했을 때
    const searchClick = () => {
        if (search === "" && cinemaName === "전체") {
            alert("검색할 단어를 입력해주세요");
        }
        else {
            setSearchWords([...searchWords, search]);
        }
    }

    useEffect(()=>{
        //처음에 최신순으로 요청
        axios.get('http://localhost:8080/information-share/search',{
        withCredentials: true,
        params: {
                    search_word : search,
                    sort_name: sort,
                }
        })
        .then(response => setInfos(response.data))
        .catch(error => console.log(error));


        //여기서 infos는 아직 빈 배열

    }, [])

    useEffect(()=>{
        dispatch({
            type:INFO,
            info:infos,
        });
        console.log(infos);
        setInfoIsHere(true);
    }, [infos])

    useEffect(()=>{
        if(cinemaName === "전체"){
            axios.get('http://localhost:8080/information-share/search',{
            withCredentials: true,
            params: {
                        search_word : search,
                        sort_name: sort,
                    }
            })
            .then(response => setInfos(response.data))
            .catch(error => console.log(error));
        } else if(cinemaArea === "지역"){
            axios.get('http://localhost:8080/information-share/search',{
            withCredentials: true,
            params: {
                        search_word : search,
                        cinema_name : cinemaName,
                        sort_name: sort,
                    }
            })
            .then(response => setInfos(response.data))
            .catch(error => console.log(error));
        } else if(cinemaBranch === "지점"){
            axios.get('http://localhost:8080/information-share/search',{
            withCredentials: true,
            params: {
                        search_word : search,
                        cinema_name : cinemaName,
                        cinema_area : cinemaArea,
                        sort_name: sort,
                    }
            })
            .then(response => setInfos(response.data))
            .catch(error => console.log(error));
        } else {
            axios.get('http://localhost:8080/information-share/search',{
            withCredentials: true,
            params: {
                        search_word : search,
                        cinema_name : cinemaName,
                        cinema_area : cinemaArea,
                        cinema_branch: cinemaBranch,
                        sort_name: sort,
                    }
            })
            .then(response => setInfos(response.data))
            .catch(error => console.log(error));
        }

    }, [searchWords])


    return(
        <>
        <select onChange={cinemaNameChange}>
            {cinemaNames.map((item) => (
                <option value={item} key={item}>{item}</option>
            ))}
        </select>
        {
            cinemaNameSelected ? <select onChange={cinemaAreaChange}>
            {cinemaAreas.map((item) => (
                <option value={item} key={item}>{item}</option>
            ))}
        </select> : null
        }

        {
            cinemaAreaSelected ? <select onChange={cinemaBranchChange}>
            {cinemaBranches.map((item) => (
                <option value={item} key={item}>{item}</option>
            ))}
        </select> : null
        }

        <select onChange={sortChange}>
            {sorts.map((item) => (
                <option value={item} key={item}>{item}</option>
            ))}
        </select>

        <div>
            <input type="text" placeholder="검색" onChange={searchChange} value={search}></input>
            <button id="searchButton" onClick={searchClick}>검색</button>
        </div>

        {useMemo(() => (infoIsHere ? <InformationShares infos={infos}/> : null), [searchWords, infos])}

        <div>
            <Link to={`/informationShareWrite`}>
            <button>글쓰기</button>
            </Link>
        </div>
        </>
    );
}

export default InformationSharePage;