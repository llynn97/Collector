package moviegoods.movie.direct_message;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.Status;
import moviegoods.movie.direct_message.messageRepository.DirectMessageReportRepository;
import moviegoods.movie.direct_message.messageRepository.DirectMessageTransactionRepository;
import moviegoods.movie.direct_message.request.DirectMessageRequestComplete;
import moviegoods.movie.direct_message.request.DirectMessageRequestReliability;
import moviegoods.movie.direct_message.request.DirectMessageRequestReport;
import moviegoods.movie.domain.Content_detail;
import moviegoods.movie.domain.Report;
import moviegoods.movie.domain.Transaction;
import moviegoods.movie.domain.User;
import moviegoods.movie.information_share.InformationRepository.InformationShareUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final DirectMessageTransactionRepository directMessageTransactionRepository;
    private final InformationShareUserRepository informationShareUserRepository;
    private final DirectMessageReportRepository directMessageReportRepository;
    public Boolean updateTransactionComplete(DirectMessageRequestComplete dmrc){
        Boolean check;
        Long transaction_id=dmrc.getTransaction_id();
        Transaction transaction=directMessageTransactionRepository.findById(transaction_id).get();
        transaction.setStatus(Status.마감);
        Transaction t=directMessageTransactionRepository.save(transaction);
        if(t.getStatus().equals(Status.마감)){
            check=true;
        }else{
            check=false;
        }
        return check;

    }

    public Boolean updateReliability(DirectMessageRequestReliability dmrr){ //메세지생성창에서 user_id 받아와야함
        Boolean check=false;
        Long user_id=dmrr.getUser_id();
        User user= informationShareUserRepository.findById(user_id).get();
        Long n= user.getReliability()+1;
        user.setReliability(n);
        User u=informationShareUserRepository.save(user);
        if(u.getReliability()==n){
            check=true;
        }
        return check;

    }

    public Report report(DirectMessageRequestReport dmrr){
        Long user_id=dmrr.getUser_id();
        Long transaction_id=dmrr.getTransaction_id();
        String report_content=dmrr.getReport_content();
         Report report=new Report();
        User user=informationShareUserRepository.findById(user_id).get();
        Transaction transaction=directMessageTransactionRepository.findById(transaction_id).get();
        Content_detail content_detail=new Content_detail();
        content_detail.setContent(report_content);
        content_detail.setWritten_date(LocalDateTime.now());
        user.getReports().add(report);
        report.setUser(user);
        content_detail.setReport(report);
        report.setContent_detail(content_detail);
        transaction.getReports().add(report);
        report.setTransaction(transaction);
        Report report1=directMessageReportRepository.save(report);

       return  report1;

    }


}
