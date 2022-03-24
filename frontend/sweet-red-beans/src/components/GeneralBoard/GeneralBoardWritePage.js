import React, { useEffect, useState } from "react";
import { CGVarea, CGV0, CGV1, CGV2, CGV3, CGV4, CGV5, CGV6, CGV7, CGV8 } from "../../cinemas/CGVcinemas";
import { LCarea, LC0, LC1, LC2, LC3, LC4, LC5, LC6, LC7 } from "../../cinemas/LCcinemas";
import { MBarea, MB0, MB1, MB2, MB3, MB4, MB5, MB6 } from "../../cinemas/MBcinemas";
import { CQarea, CQ0, CQ1, CQ2, CQ3, CQ4 } from "../../cinemas/CQcinemas";
import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import style from "../../css/GeneralBoardPage/GeneralBoardWritePage.module.css";

const GeneralBoardWritePage = () => {
    let navigation = useNavigate();

    const [array, setArray] = useState([]);
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    //사진 첨부
    const [imgFile, setImgFile] = useState(null);
    const [imgBase64, setImgBase64] = useState(null);

    useEffect(() => {
        document.documentElement.scrollTop = 0;
    }, [])

    const contentChange = (e) => {
        // const a = content.replace("\n", <br/>)
        // console.log(a);
        setContent(e.target.value);
    }

    //const userId = useSelector(state => state.user.user_id);

    //글쓰기 버튼 눌렀을 때
    const writeClick = () => {
        if (title === "" || content === ""){
            alert("내용을 입력해주세요.");
        }
        else {
            const fd = new FormData();

            //이미지 파일 없을 때
            if(imgFile === null){
                fd.append("user_id", "1");
                fd.append("title", title);
                fd.append("content", content);

                axios.post('http://localhost:8080/general-board/write', fd, {
                    withCredentials: true,
                    headers: {
                        "Content-Type": `multipart/form-data; `,
                    }
                })
                .then(response => {
                    if(response.data.result) {
                        alert("게시글이 성공적으로 작성되었습니다.");
                        navigation('/GeneralBoard');
                    }
                    else {
                        alert("게시글 작성을 실패했습니다.")
                    }
                })
                .catch(error => {
                    if(error.response.status === 401){
                        alert("로그인을 먼저 해주세요");
                    }
                });
            }
            //이미지 파일 있을 때
            else {
                fd.append("image_url", imgFile[0])
                fd.append("user_id", "1");
                fd.append("title", title);
                fd.append("content", content);

                axios.post('http://localhost:8080/general-board/write', fd, {
                    withCredentials: true,
                    headers: {
                        "Content-Type": `multipart/form-data; `,
                    }
                })
                .then(response => {
                    if(response.data.result) {
                        alert("게시글이 성공적으로 작성되었습니다.");
                        navigation('/GeneralBoard');
                    }
                    else {
                        alert("게시글 작성을 실패했습니다.")
                    }
                })
                .catch(error => {
                    if(error.response.status === 401){
                        alert("로그인을 먼저 해주세요");
                    }
                });
            }
        }
    }

    const titleChange = (e) => {
        setTitle(e.target.value);
    }

    const handleChangeFile = (e) => {
        console.log(e.target.files)
        setImgFile(e.target.files);
    }

    useEffect(() => {
        if(!imgFile) return false;

        const reader = new FileReader();
        reader.onloadend = () => {
            setImgBase64(reader.result);
        }
        reader.readAsDataURL(imgFile[0]);

    }, [imgFile])

    const previewCancelClick = () => {
        setImgFile(null);
        setImgBase64(null);
    }

    return(
        <>
        <div className={style.whiteBox}>
            <div className={style.titleArea}>
                <input type="text" value={title} onChange={titleChange} placeholder="제목" maxLength="50"/>
            </div>
            
            <div className={style.contentArea}>
                <textarea onChange={contentChange} value={content} placeholder="내용을 입력해주세요"/>
            </div>

            <div className={style.buttonArea}>
                <label htmlFor="upload_file"></label>
                <input type="file" onChange={handleChangeFile} id="upload_file" style={{display:"none"}}/>
                {imgBase64 !== null ?
                <div>
                    <img src={imgBase64}/>
                    <button onClick={previewCancelClick}></button>
                </div>
                : null}
                <button onClick={writeClick}>글쓰기</button>
            </div>

            
        </div>
        </>
    );
}

export default GeneralBoardWritePage;