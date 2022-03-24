import React, { useEffect, useState, useRef } from 'react';
import MyPageDetail from './MyPageDetail';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import {
    MYPAGE_USER,
    MYPAGE_TRANSACTIONS,
    MYPAGE_COMMENTS,
    MYPAGE_EVENTS,
    MYPAGE_POSTS,
    MYPAGE_LIKE_TRANSACTIONS,
} from '../../actions/types';
import style from '../../css/MyPage/MyPageNormal.module.css';
import { Outlet, useNavigate } from 'react-router';
import { Route, Routes } from 'react-router-dom';
import MyEvents from './MyPageDetail/MyEvents';
import { Link } from 'react-router-dom';

const MyPageNomal = () => {
    //서버에서 받아온 정보들 저장하기
    const [profileImage, setProfileImage] = useState('');
    const [nickname, setNickname] = useState('');
    const [reliability, setReliability] = useState('');

    //프로필 수정
    const [hide, setHide] = useState(true);
    //이미지 불러오기
    const [imgFile, setImgFile] = useState(null);
    //닉네임 변경용
    const [nicknameModify, setNicknameModify] = useState('');

    const [preImage, setPreImage] = useState(null);

    //닉네임 중복 확인이 성공적으로 됐을 때 true
    const [nicknameCheck, setNicknameCheck] = useState(false);
    //닉네임 중복 확인 안 됐을 때 false
    const [nicknameError, setNicknameError] = useState(false);

    const [renderError, setRenderError] = useState(true);

    const dispatch = useDispatch();
    const navigation = useNavigate();

    useEffect(() => {
        axios
            .get('http://localhost:8080/mypage', {
                withCredentials: true,
            })
            .then((response) => {
                setNickname(response.data.user.nickname);
                setNicknameModify(response.data.user.nickname);
                setProfileImage(response.data.user.profile_url);
                console.log(response.data.user.profile_url);
                setReliability(response.data.user.reliability);
                dispatch({
                    type: MYPAGE_USER,
                    payload: response.data.user,
                });
                dispatch({
                    type: MYPAGE_TRANSACTIONS,
                    payload: response.data.writeTransaction,
                });
                dispatch({
                    type: MYPAGE_COMMENTS,
                    payload: response.data.comment,
                });
                dispatch({
                    type: MYPAGE_EVENTS,
                    payload: response.data.likeEvent,
                });
                dispatch({
                    type: MYPAGE_POSTS,
                    payload: response.data.content,
                });
                dispatch({
                    type: MYPAGE_LIKE_TRANSACTIONS,
                    payload: response.data.likeTransaction,
                });
                setRenderError(false);
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    alert('로그인을 먼저 해주세요');
                    setRenderError(true);
                    navigation('/');
                }
            });
    }, []);

    //메뉴 선택
    const myListClick = (index, e) => {
        navigation('/mypage/' + index);
    };

    //프로필 변경 버튼 눌렀을 때
    const profileChangeClick = () => {
        setHide(false);
        setNicknameCheck(false);
        setNicknameError(false);
    };

    //프로필 변경 취소 버튼 눌렀을 때
    const profileCancelClick = () => {
        setHide(true);
    };

    //프로필 변경 완료 버튼 눌렀을 때
    const profileCompleteClick = () => {
        const fd = new FormData();

        //프로필 사진 바뀌었을 때
        if (imgFile !== null) {
            fd.append('file', imgFile[0]);
            fd.append('user_id', '1');
            axios
                .patch('http://localhost:8080/mypage/profile', fd, {
                    withCredentials: true,
                    headers: {
                        'Content-Type': `multipart/form-data; `,
                    },
                })
                .then((response) => {
                    if (response.data) {
                        setImgFile(null);
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
            setHide(true);
        }

        //닉네임 바뀌었을 때
        if (nickname !== nicknameModify) {
            if (!nicknameCheck) {
                console.log('체크 안함');
                alert('중복체크를 해주세요');
                return setNicknameError(true);
            } else {
                axios
                    .patch(
                        'http://localhost:8080/mypage/nickname',
                        {
                            nickname: nicknameModify,
                        },
                        { withCredentials: true }
                    )
                    .then((response) => {
                        if (response.data) {
                            console.log('닉네임 변경됨');
                            //닉네임 변경되면 변경된 닉네임으로 바꿔줌
                            setNickname(nicknameModify);
                        }
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                setHide(true);
            }
        }

        navigation(0);
    };

    const handleChangeFile = (e) => {
        setImgFile(e.target.files);
    };

    useEffect(() => {
        preview();
        return () => preview();
    });

    const preview = () => {
        if (!imgFile) return false;

        const imgEl = document.querySelector('.preview');
        const reader = new FileReader();
        reader.onloadend = () => {
            //imgEl.style.backgroundImage = `url(${reader.result})`
            setPreImage(reader.result);
            //setProfileImage(reader.result);
        };
        reader.readAsDataURL(imgFile[0]);
    };
    const nicknameModifyChange = (e) => {
        setNicknameModify(e.target.value);
    };

    useEffect(() => {}, [profileImage]);

    useEffect(() => {
        console.log(nicknameCheck);
    }, [nicknameCheck]);

    //중복확인 버튼 누를 때
    const nicknameModifyClick = () => {
        //바뀌었을 때
        if (nickname !== nicknameModify) {
            const userNickname = { nickname: nicknameModify };
            axios
                .post(
                    'http://localhost:8080/mypage/duplicate-check',
                    userNickname,
                    { withCredentials: true }
                )
                .then((response) => {
                    const data = response.data;
                    if (data.result === false) {
                        alert('사용할 수 있는 닉네임입니다.');
                        setNicknameCheck(true);
                        setNicknameError(false);
                    } else {
                        alert('사용할 수 없는 닉네임입니다.');
                    }
                });
        } else {
            //바뀌지 않았으면 닉네임 중복 체크 안 해도 됨
            setNicknameCheck(true);
            setNicknameError(false);
        }
    };

    const useConfirm = (message = null, onConfirm, onCancel) => {
        if (!onConfirm || typeof onConfirm !== 'function') {
            return;
        }
        if (onCancel && typeof onCancel !== 'function') {
            return;
        }

        const confirmAction = () => {
            if (window.confirm(message)) {
                onConfirm();
            } else {
                onCancel();
            }
        };

        return confirmAction;
    };

    //삭제버튼 눌렀을 때
    const deleteConfirm = () => {
        axios
            .delete('http://localhost:8080/mypage/withdrawal', {
                withCredentials: true,
                data: {},
            })
            .then((response) => {
                if (response.data.result) {
                    alert('탈퇴 되었습니다.');
                    //이전 페이지로 이동
                    navigation('/');
                } else {
                    alert('탈퇴에 실패했습니다.');
                }
            })
            .catch((error) => console.log(error));
    };

    const cancelConfirm = () => console.log('탈퇴 취소');

    const quitClick = useConfirm(
        '정말 탈퇴하시겠습니까?',
        deleteConfirm,
        cancelConfirm
    );

    return (
        <>
            {!renderError ? (
                <>
                    <div className={style.profileWhiteBox}>
                        {hide ? (
                            <div className={style.profileBox}>
                                <div>
                                    <img src={profileImage} />
                                </div>
                                <div>{nickname}</div>
                                <div>
                                    <button onClick={profileChangeClick}>
                                        프로필 수정하기
                                    </button>
                                </div>

                                <div>{reliability}</div>
                            </div>
                        ) : null}

                        {!hide ? (
                            <div className={style.profileChangeBox}>
                                {preImage !== null ? (
                                    <img src={preImage} />
                                ) : (
                                    <img src={profileImage} />
                                )}
                                <label htmlFor="upload_file"></label>
                                <input
                                    type="file"
                                    onChange={handleChangeFile}
                                    id="upload_file"
                                    style={{ display: 'none' }}
                                />

                                <div
                                    className={style.nicknameChangeArea}
                                    id="nicknameChangeArea">
                                    <input
                                        type="text"
                                        placeholder="닉네임"
                                        onChange={nicknameModifyChange}
                                        value={nicknameModify}
                                        maxLength="15"
                                    />
                                    <div></div>
                                </div>

                                <button
                                    onClick={nicknameModifyClick}
                                    className={style.nicknameDupButton}>
                                    중복 확인하기
                                </button>
                                {nicknameError && (
                                    <div className={style.nicknameDupText}>
                                        닉네임 중복확인해주세요
                                    </div>
                                )}
                                <button
                                    onClick={profileCompleteClick}
                                    className={style.completeButton}>
                                    완료
                                </button>
                                <button
                                    onClick={profileCancelClick}
                                    className={style.cancelButton}>
                                    취소
                                </button>
                            </div>
                        ) : null}
                    </div>

                    <div className={style.detailMenuArea}>
                        <nav>
                            <ul>
                                <li onClick={(e) => myListClick('myevents', e)}>
                                    <button></button>
                                    <div>관심있는 이벤트</div>
                                </li>
                                <li
                                    onClick={(e) =>
                                        myListClick('mytransactions', e)
                                    }>
                                    <button></button>
                                    <div>내가 쓴 거래</div>
                                </li>
                                <li
                                    onClick={(e) =>
                                        myListClick('myliketransactions', e)
                                    }>
                                    <button></button>
                                    <div>내가 좋아요 한 거래</div>
                                </li>
                                <li onClick={(e) => myListClick('myposts', e)}>
                                    <button></button>
                                    <div>내가 쓴 글</div>
                                </li>
                                <li
                                    onClick={(e) =>
                                        myListClick('mycomments', e)
                                    }>
                                    <button></button>
                                    <div>내가 쓴 댓글</div>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div className={style.detail}>
                        <Outlet />
                    </div>
                    <div className={style.quitArea}>
                        <button onClick={quitClick}>탈퇴하기</button>
                    </div>
                </>
            ) : null}
        </>
    );
};

export default MyPageNomal;
