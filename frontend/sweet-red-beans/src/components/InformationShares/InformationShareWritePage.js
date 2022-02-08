import React, { useEffect, useState } from "react";
import { CGVarea, CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8 } from "../../cinemas/CGVcinemas";
import { LCarea, LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7 } from "../../cinemas/LCcinemas";
import { MBarea, MB0, MB1, MB2, MB3, MB4, MB5, MB6 } from "../../cinemas/MBcinemas";
import { CQarea, CQ0, CQ1, CQ2, CQ3, CQ4 } from "../../cinemas/CQcinemas";
import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";

const InformationShareWritePage = () => {
    let navigation = useNavigate();
    //사용자가 작성한 글 서버에 넘기기
    console.log("정보공유 작성 페이지 렌더");
    const cinemaNames = ["영화관", "CGV", "롯데시네마", "메가박스", "씨네큐"];
    const CGVarray = [CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8];
    const LCarray = [LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7];
    const MBarray = [MB0, MB1, MB2, MB3, MB4, MB5, MB6];
    const CQarray = [CQ0, CQ1, CQ2, CQ3, CQ4];

    //필터에서 선택한 영화사, 지역, 지점 이름
    const [cinemaName, setCinemaName] = useState("영화관");
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

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const cinemaNameChange = (e) => {
        if(e.target.value === "영화관"){
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

    const cinemaAreaChange = (e) => {
        if(e.target.value === "지역"){
            setCinemaAreaSelected(false);
            setCinemaBranchSelected(false);
        }
        else {
            setCinemaArea(e.target.value);
            setCinemaAreaSelected(true);
            setCinemaBranches(array[cinemaAreas.indexOf(e.target.value)-1]);
        }
    }

    const cinemaBranchChange = (e) => {
        setCinemaBranch(e.target.value);
        setCinemaBranchSelected(true);
    }

    const contentChange = (e) => {
        setContent(e.target.value);
    }

    const userId = useSelector(state => state.user.user_id);

    const writeClick = () => {
        console.log(userId);
        if (title === "" || content === ""){
            alert("내용을 입력해주세요.");
        }
        else {
            const body = {
                user_id : userId,
                title: title,
                cinema_name : cinemaName,
                cinema_area: cinemaArea,
                cinema_branch: cinemaBranch,
                content: content,
                image_url: "이미지url",
            }
            axios.post('http://localhost:8080/information-share/write', body)
            .then(response => {
                if(response.data.result) {
                    alert("게시글이 성공적으로 작성되었습니다.");
                    navigation('/informationShare');
                }
                else {
                    alert("게시글 작성을 실패했습니다.")
                }
            })
            .catch(error => console.log(error));
        }
    }

    const titleChange = (e) => {
        setTitle(e.target.value);
    }

    return(
        <>
        <input type="text" value={title} onChange={titleChange}/>
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

        <button>사진 첨부</button>
        <div>
        <input type="textarea" style={{width: "400px", height:"200px"}} onChange={contentChange} value={content}/>
        </div>
        <button onClick={writeClick}>글 작성하기</button>
        </>
    );
}

export default InformationShareWritePage;