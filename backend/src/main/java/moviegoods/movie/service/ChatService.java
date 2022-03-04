package moviegoods.movie.service;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import moviegoods.movie.domain.dto.directMessage.*;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.ChatRoom.Chat_Room;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Message.Message;
import moviegoods.movie.domain.entity.Message.MessageRepository;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.Report.ReportRepository;
import moviegoods.movie.domain.entity.Transaction.Status;
import moviegoods.movie.domain.entity.Transaction.Transaction;
import moviegoods.movie.domain.entity.Transaction.TransactionRepository;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static moviegoods.movie.domain.entity.Transaction.Status.진행중;


@Slf4j
@Service
@AllArgsConstructor
public class ChatService {

    private final UserRepository informationShareUserRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final TransactionRepository directMessageTransactionRepository;
    private final ReportRepository directMessageReportRepository;
    private final FireBaseService fireBaseService;
    private final ContentDetailService contentDetailService;

    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(User loginUser, DirectMessage message) throws IOException, FirebaseAuthException {

        Long user_id = null;
        if (loginUser != null) {
            user_id = loginUser.getUser_id();
        }
        User user=loginUser;

        Chat_Room chat_room=chatRoomRepository.findById(message.getChat_room_id()).get();
        String content = message.getContent();
        Message saveEntity;


        String firebaseUrl="";
        if(message.getImage_url()==null){
            Content_Detail content_detail = contentDetailService.saveContentDetail(content);
            saveEntity = Message.builder().user(user).content_detail(content_detail).chat_room(chat_room).build();

        }else {

            MultipartFile image_url=message.getImage_url();
            String nameFile= UUID.randomUUID().toString();
            log.info("nameFile={}", nameFile);
            fireBaseService.uploadFiles(image_url,nameFile);
            firebaseUrl+="https://firebasestorage.googleapis.com/v0/b/stroagetest-f0778.appspot.com/o/"+nameFile+"?alt=media";

            saveEntity = Message.builder().user(user).image_url(firebaseUrl).chat_room(chat_room).build();
//
//            message1.setImage_url(firebaseUrl);
        }


//        //content_detail.setMessage(message1);
//        message1.setContent_detail(content_detail);
//        message1.setUser(user);
//        //user.getMessages().add(message1);
//        message1.setChat_room(chat_room);
//        //chat_room.getMessages().add(message1);

        messageRepository.save(saveEntity);

    }

    public List<DirectMessageDetailResponseDto> show(User loginUser, Long room_id) {
        List<DirectMessageDetailResponseDto> messagesList = new ArrayList<>();
        Optional<Chat_Room> chatRoom = chatRoomRepository.findById(room_id);
        Chat_Room findedRoom = chatRoom.get();

        List<Message> messages = findedRoom.getMessages();
        for (Message message : messages) {
            Content_Detail content_detail = message.getContent_detail();
            String content = content_detail.getContent();
            LocalDateTime written_date = content_detail.getWritten_date();
            String image_url = message.getImage_url();

            User user = message.getUser();
            Long user_id = user.getUser_id();
            String nickname = user.getNickname();
            String profile_url = user.getProfile_url();
            Long reliability = user.getReliability();

            messagesList.add(new DirectMessageDetailResponseDto(content, written_date, image_url, user_id, nickname, profile_url, reliability));
        }
        return messagesList;
    }

    public Boolean updateTransactionComplete(User loginUser, DirectMessageRequestComplete dmrc){
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

    public Boolean updateReliability(User loginUser, DirectMessageRequestReliability dmrr){ //메세지생성창에서 user_id 받아와야함
        Boolean check=false;
        Long user_id = null;
        if (loginUser != null) {
            user_id = loginUser.getUser_id();
        }
        User user= loginUser;
        Long n= user.getReliability()+1;
        user.setReliability(n);
        User u=informationShareUserRepository.save(user);
        if(u.getReliability()==n){
            check=true;
        }
        return check;

    }

    public Report report(User loginUser, DirectMessageRequestReport dmrr){
        Long user_id = null;
        if (loginUser != null) {
            user_id = loginUser.getUser_id();
        }
        Long transaction_id=dmrr.getTransaction_id();
        String report_content=dmrr.getReport_content();
        Report report=new Report();
        User user=loginUser;
        Transaction transaction=directMessageTransactionRepository.findById(transaction_id).get();
        Content_Detail content_detail=new Content_Detail();
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
