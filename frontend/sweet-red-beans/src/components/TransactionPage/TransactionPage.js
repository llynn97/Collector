import React, { useState, useEffect, useMemo, useRef } from "react";
import Transactions from "./Transactions";
import style from "../../css/TransactionPage/TransactionPage.module.css";
import axios from "axios";
import TransactionWriteModal from "../Modals/TransactionWriteModal";
import InfiniteScroll from "./InfiniteScroll";
import { getCookie } from "../../Cookie";
import { useNavigate } from "react-router";

const TransactionPage = () => {
    const navigation = useNavigate()
    const [modalOpen, setModalOpen] = useState(false);
    const [transactions, setTransactions] = useState([]);
    const [transactionIsHere, setTransactionIsHere] = useState(false);
    const [search, setSearch] = useState("");
    const [searchWords, setSearchWords] = useState([]);
    //처음엔 전체로 보여줌, true이면 진행 중만 보여줌
    const [isProceed, setIsProceed] = useState(false);
    //검색 필터
    const [searchSort, setSearchSort] = useState("글내용");
    // 스크롤값을 저장하기 위한 상태
    const [ScrollY, setScrollY] = useState(0);
    const [BtnStatus, setBtnStatus] = useState(false);
    //내용
    const [content, setContent] = useState("");

    const [start, setStart] = useState(0);
    const [end, setEnd] = useState(29);

    const [fetching, setFetching] = useState(false);

    const serverReq = () => {
      axios.get('http://localhost:8080/transactions/search',{ 
          withCredentials: true,
          params: {
                    user_id: "1",
                    is_proceed: isProceed,
                    search_word: search,
                    sort_criteria : "최신순",
                    search_criteria : searchSort,
                    start: start+30,
                    end: end+30,
                  }
        }
      )
      .then(response => {
        setTransactions(response.data)
        setStart(0);
        setEnd(29);
      })
      .catch(error => console.log(error));
  }

    const searchChange = (e) => {
        setSearch(e.target.value);
    }

    //검색버튼 눌렀을 때
    const searchClick = () => {
      if (search === "") {
        alert("검색할 단어를 입력해주세요");
      }
      else {
        setSearchWords([...searchWords, search]);
      }
    }

    //전체보기 눌렀을 때
    const allClick = () => {
        setIsProceed(false);
        console.log("전체 보기");
    }

    //진행중만 보기 눌렀을 때
    const proceedClick = () => {
        setIsProceed(true);
        console.log("진행 중만 보기");
    }

    //글내용 or 작성자 변경했을 때
    const searchSortChange = (e) => {
        setSearchSort(e.target.value);
    }

    //내용 변경됐을 때
    const contentChange = (e) => {
        setContent(e.target.value);
        console.log(e.target.value);
    }

    //글쓰기 버튼 클릭했을 때
    const writeClick = () => {
      if(content === "") {
        return
      }
      else {
        const body = {
          content: content,
          user_id: "1",
        }
  
        axios.post('http://localhost:8080/transactions/write', body, { withCredentials: true })
        .then(response => {
          if(response.data){
            axios.get('http://localhost:8080/transactions/search',{ 
            withCredentials: true,
            params: {
                      user_id: "1",
                      is_proceed: isProceed,
                      search_word: search,
                      sort_criteria : "최신순",
                      search_criteria : searchSort,
                      start: start+30,
                      end: end+30,
                    }
          })
          .then(response => {
            setTransactions(response.data);
          })
          .catch(error => console.log(error));
          }

          else {
            alert("글 작성에 실패했습니다.");
          }
        })
        .catch(error => console.log(error));
        
        setContent("");
        setModalOpen(false)
      }
    }

    const openModal = () => {
      setModalOpen(true);
    };
    const closeModal = () => {
      setModalOpen(false);
    };

    const handleFollow = () => {
      setScrollY(window.pageYOffset);
      if(ScrollY > 100) {
        // 100 이상이면 버튼이 보이게
        setBtnStatus(true);
      } else {
        // 100 이하면 버튼이 사라지게
        setBtnStatus(false);
      }
    }

    const handleTop = () => {  // 클릭하면 스크롤이 위로 올라가는 함수
      window.scrollTo({
          top: 0,
          behavior: "smooth"
      });
      setScrollY(0);  // ScrollY 의 값을 초기화
      setBtnStatus(false); // BtnStatus의 값을 false로 바꿈 => 버튼 숨김
    }

    const fetchMoreTransactions = () => {
      if(transactions.length !== 0) {
        axios.get('http://localhost:8080/transactions/search',{ 
          withCredentials: true,
          params: {
                    user_id: "1",
                    is_proceed: isProceed,
                    search_word: search,
                    sort_criteria : "최신순",
                    search_criteria : searchSort,
                    start: start+30,
                    end: end+30,
                  }
        })
          .then(response => {
            const more = response.data;
            const mergeData = transactions.concat(...more);
            setTransactions(mergeData);
            setStart(start+30);
            setEnd(end+30);
          })
          .catch(error => console.log(error));
      }


      console.log("더 붙이기");
    }

    useEffect(() => {
      //console.log("ScrollY is ", ScrollY); // ScrollY가 변화할때마다 값을 콘솔에 출력

      //ScrollY === document.documentElement.scrollTop
      if (ScrollY + document.documentElement.clientHeight >= document.documentElement.scrollHeight){
        if(transactions.length >= 30){
          fetchMoreTransactions();
        }
      }
    }, [ScrollY])
  
    useEffect(() => {
      const watch = () => {
        window.addEventListener('scroll', handleFollow);
      }
      watch(); // addEventListener 함수를 실행
      return () => {
        window.removeEventListener('scroll', handleFollow); // addEventListener 함수를 삭제
      }
    })

    //한 번만 실행
    useEffect(()=>{
      axios.get('http://localhost:8080/transactions/search',{ 
          withCredentials: true,
          params: {
                  is_proceed: isProceed,
                  search_word: search,
                  sort_criteria : "최신순",
                  search_criteria : searchSort,
                  start:start,
                  end:end,
                }
        })
        .then(response => {
          setTransactions(response.data);
        })
        .catch(error => console.log(error));
    }, [])

    useEffect(()=>{
      setTransactionIsHere(true);
    }, [transactions])

    useEffect(() => {
      serverReq();
      console.log(isProceed);
    }, [isProceed]);

    useEffect(() => {
      serverReq();
      console.log(searchWords, searchSort);
    }, [searchWords])

    return (
        <>
        <div className={style.wrap}>
        <div className={style.writeBox}>
          <div>
          <div className={style.writeButtonArea}>
            <button onClick={writeClick} className={style.writeButton}>작성하기</button>
          </div>
          <textarea value={content} onChange={contentChange} className={style.writeTextArea}></textarea>
          </div>
        </div>

        <div className={style.transactionsBox}>
          <div className={style.topBox}>
            <button onClick={allClick} className={style.all}>전체</button>
            <button onClick={proceedClick} className={style.proceeding}>진행중</button>
            <div className={style.filterBox}>
              <select onChange={searchSortChange} className={style.filter}>
                  <option value="글내용">글내용</option>
                  <option value="작성자">작성자</option>
              </select>
              <span className={style.filterArrow}>▼</span>
            </div>
            <div className={style.searchBox}>
              <input type="text" placeholder="검색" value={search} onChange={searchChange} className={style.searchArea}/>
              <button onClick={searchClick} className={style.searchButton}></button>
            </div>
          </div>
          
          {useMemo(() => transactionIsHere ? <Transactions transactions={transactions}/> :null, [searchWords, transactions, isProceed])}
        </div>



        <button className={BtnStatus ? [style.topBtn, style.active].join(" ") : style.topBtn} onClick={handleTop}>TOP</button>

        <button className={BtnStatus ? [style.writeBtn, style.active].join(" ") : style.writeBtn} onClick={openModal}>글쓰기</button>
        <TransactionWriteModal open={modalOpen} close={closeModal} header="글 작성하기">
          <textarea value={content} onChange={contentChange} style={{width:"400px", height:"200px", cols:"20"}}></textarea>
          <button onClick={writeClick}>글 쓰기</button>
        </TransactionWriteModal>

        <div>
        
        </div>

        </div>
        
        </>
    );
}

export default TransactionPage;