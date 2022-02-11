package moviegoods.movie.controller;


import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;

import moviegoods.movie.domain.dto.directMessage.DirectMessage;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestComplete;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestReliability;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestReport;
import moviegoods.movie.domain.dto.informationShare.Result;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.Message.MessageRepository;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.FireBaseService;
import moviegoods.movie.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/direct-message")
public class ChatController {
    private final UserRepository informationShareUserRepository;
   // private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final FireBaseService fireBaseService;

    /* @MessageMapping("/chat/message")
    public void message(DirectMessage message){
         if(message.getMessageType().equals(DirectMessage.MessageType.ENTER)) {
             message.setContent(message.getNickname() + "입장");

         }
         Message message1=new Message();
         Long user_id = message.getUser_id();
         User user=informationShareUserRepository.findById(user_id).get();
         Chat_Room chat_room=chatRoomRepository.findById(message.getChat_room_id()).get();
         Content_detail content_detail=new Content_detail();
         content_detail.setWritten_date(LocalDateTime.now());
         if(message.getImage_url()==null){
             content_detail.setContent(message.getContent());
         }else {

          //   message1.setImage_url(message.getImage_url());
         }

         content_detail.setMessage(message1);
         message1.setContent_detail(content_detail);
         user.getMessages().add(message1);
         message1.setUser(user);
         chat_room.getMessages().add(message1);
         message1.setChat_room(chat_room);
         messageRepository.save(message1);

         messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChat_room_id());


     }*/


     @PostMapping("/detail")
     public Result saveMessage(@ModelAttribute DirectMessage message) throws IOException, FirebaseAuthException {

         messageService.saveMessage(message);
         Result result=new Result();
         result.setResult(true);
         return result;

     }

    @PostMapping("/transaction-complete")
    public Result complete(@RequestBody DirectMessageRequestComplete dmrc){
        Boolean check= messageService.updateTransactionComplete(dmrc);
        Result result=new Result();
        if(check==true){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @PostMapping("/reliability")
    public Result updateReliability(@RequestBody DirectMessageRequestReliability dmrr){
        Boolean check=messageService.updateReliability(dmrr);
        Result result=new Result();
        if(check==true){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @PostMapping("/report")
    public Result Report(@RequestBody DirectMessageRequestReport dmrr){
        Report report=messageService.report(dmrr);
        Result result=new Result();
        if(report!=null){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

}
