package moviegoods.movie.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.entity.Content_Detail.ContentDetailRepository;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.Report.ReportRepository;
import moviegoods.movie.domain.entity.Transaction.Status;
import moviegoods.movie.domain.entity.Transaction.Transaction;
import moviegoods.movie.domain.entity.Transaction.TransactionRepository;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.ContentDetailService;
import moviegoods.movie.domain.dto.transactions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static moviegoods.movie.domain.entity.Transaction.Status.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionsService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ContentDetailRepository contentDetailRepository;
    private final ReportRepository reportRepository;
    private final ContentDetailService contentDetailService;
    private final LikeBasketsService likeBasketsService;
    private final EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public Boolean write(TransactionsSaveRequestDto requestDto) {
        Long user_id = requestDto.getUser_id();
        String content = requestDto.getContent();
        Status status = 진행중;

        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. user_id = {}"+ user_id));
        Content_Detail content_detail = contentDetailService.saveContentDetail(content);
        Transaction saveEntity = Transaction.builder().user(user).content_detail(content_detail).status(status).build();

        transactionRepository.save(saveEntity);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<TransactionsSearchResponseDto> search(TransactionsSearchRequestDto requestDto) {
        List<TransactionsSearchResponseDto> searchList = new ArrayList<>();

        Long user_id = requestDto.getUser_id();
        Boolean is_proceed = requestDto.getIs_proceed(); // 모집중(1)
        String search_word = requestDto.getSearch_word(); // 검색어
        String sort_criteria = requestDto.getSort_criteria(); // 최신순
        String search_criteria = requestDto.getSearch_criteria(); // 작성자/글내용
        String linking_word = "where ";
        log.info("is_proceed={}, search_word={}, search_criteria={}, sort_criteria={}", is_proceed, search_word, search_criteria, sort_criteria);

        if (search_word == null) {
            search_word = "";
        }

        String searchJpql = "select t from transaction t ";
        Integer check = 0;

        // 작성자/글내용 (기본 값 줘야)
        String criteriaJpql = "";
        if (search_criteria != null) {
            check++;
            if (Objects.equals(search_criteria, "작성자")) {
                searchJpql += "join t.user u where ";
                criteriaJpql = "u.nickname";
            }
            if (Objects.equals(search_criteria, "글내용")) {
                searchJpql += "join t.content_detail c where ";
                criteriaJpql= "c.content";
            }
            searchJpql += criteriaJpql + " like '%" + search_word + "%' ";
        }

        // 진행여부
        if (Objects.equals(is_proceed, true)) {
            if (check == 1) {
                linking_word = "and ";
            }
            searchJpql += linking_word+"t.status = ";
            searchJpql += "'진행중' ";
        }
        if (Objects.equals(is_proceed, false)) {
            if (check == 1) {
                linking_word = "and ";
            }
            searchJpql += linking_word+"(t.status = ";
            searchJpql += "'진행중' or t.status = '마감') ";
        }





        // 정렬 기준(최신순) 기본 값 줘야
        searchJpql += "order by c.written_date desc";
        log.info("searchJpql={}", searchJpql);

        List<Transaction> transactionList = em.createQuery(searchJpql, Transaction.class).getResultList();
        for (Transaction transaction : transactionList) {
            Long search_user_id = transaction.getUser().getUser_id();
            Long reliability = transaction.getUser().getReliability();
            String content = transaction.getContent_detail().getContent();
            String status2 = String.valueOf(transaction.getStatus());
            Long transaction_id = transaction.getTransaction_id();
            LocalDateTime written_date = transaction.getContent_detail().getWritten_date();
            String nickname = transaction.getUser().getNickname();

            Boolean is_mine = Boolean.FALSE;
            if (search_user_id == user_id) {
                is_mine = Boolean.TRUE;
            }

            Boolean is_like = likeBasketsService.isLikeTransaction(user_id, transaction_id);

            searchList.add(new TransactionsSearchResponseDto(search_user_id, content, status2, transaction_id, reliability, written_date, is_mine, is_like,nickname));
        }
        return searchList;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean changeStatus(TransactionsChangeStatusRequestDto requestDto) {
        Long user_id = requestDto.getUser_id();
        String status = requestDto.getStatus();
        Long transaction_id = requestDto.getTransaction_id();

        Transaction transaction = transactionRepository.findById(transaction_id).orElseThrow(() -> new IllegalArgumentException("해당 거래내역이 없습니다. transaction_id = {}"+ transaction_id));

        if (Objects.equals(status,"진행중")) {
            transaction.setStatus(마감);
        }
        if (Objects.equals(status, "마감")) {
            transaction.setStatus(진행중);
        }

        transactionRepository.save(transaction);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(TransactionsDeleteRequestDto requestDto) {
        Long user_id = requestDto.getUser_id();
        Long transaction_id = requestDto.getTransaction_id();

        Transaction transaction = transactionRepository.findById(transaction_id).orElseThrow(() -> new IllegalArgumentException("해당 거래내역이 없습니다. transaction_id = {}"+ transaction_id));
        Long transaction_user_id = transaction.getUser().getUser_id();

        // 예외처리하기
        if (transaction_user_id == user_id) {
            transactionRepository.delete(transaction);
            return true;
        }

        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean report(TransactionsReportRequestDto requestDto) {
        // 신고자 아이디 저장할건가??, 신고글 작성안해???, 본인이 본인글 신고가능?, 중복 신고 가능?
        Long user_id = requestDto.getUser_id();
        Long transaction_id = requestDto.getTransaction_id();

        Transaction transaction = transactionRepository.findById(transaction_id).orElseThrow(() -> new IllegalArgumentException("해당 거래내역이 없습니다. transaction_id = "+ transaction_id));
        Long content_detail_id = transaction.getContent_detail().getContent_detail_id();
        Content_Detail content_detail = contentDetailRepository.findById(content_detail_id).orElseThrow(() -> new IllegalArgumentException("해당 거래내역 메세지가 없습니다. content_detail_id = "+ content_detail_id));
        Report saveEntity = Report.builder().transaction(transaction).content_detail(content_detail).build();

        reportRepository.save(saveEntity);

        return true;
    }

}
