package moviegoods.movie.controller;


import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;

import moviegoods.movie.domain.dto.directMessage.DirectMessage;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestComplete;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestReliability;
import moviegoods.movie.domain.dto.directMessage.DirectMessageRequestReport;
import moviegoods.movie.domain.dto.informationShare.Result;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.ChatRoom.Chat_Room;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Message.Message;
import moviegoods.movie.domain.entity.Message.MessageRepository;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.FireBaseService;
import moviegoods.movie.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/direct-message")
public class ChatController {
    private final UserRepository informationShareUserRepository;
   private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final FireBaseService fireBaseService;

    @GetMapping("/detail2")
    public Result saveMessage2(@RequestBody DirectMessage message) throws IOException, FirebaseAuthException {

        messageService.saveMessage(message);
        Result result=new Result();
        result.setResult(true);
        return result;

    }
     @MessageMapping("/chat/message")
    public void message(@RequestBody DirectMessage message) throws IOException, FirebaseAuthException {
        // if(message.getMessageType().equals(DirectMessage.MessageType.ENTER)) {
          //   message.setContent(message.getNickname() + "입장");

         //}
         messageService.saveMessage(message);



         messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChat_room_id(),message);
         //pub/direct-message/chat/message

     }

   /* @MessageMapping("/chat/message")
    public void message2(@RequestParam (value = "user_id")String id, @RequestParam(value ="content" )String content,
                         @RequestParam(value = "nickname")String nickname, @RequestParam(value = "chat_room_id")String room_id,
                         @RequestParam(value = "image_url",required = false)MultipartFile file) throws IOException, FirebaseAuthException {
        // if(message.getMessageType().equals(DirectMessage.MessageType.ENTER)) {
        //   message.setContent(message.getNickname() + "입장");

        //}
        DirectMessage message=new DirectMessage();
        Long chat_room_id=Long.valueOf(room_id);
        Long user_id=Long.valueOf(id);
        message.setChat_room_id(chat_room_id);
        message.setContent(content);
        message.setImage_url(file);
        message.setNickname(nickname);
        message.setUser_id(user_id);
        messageService.saveMessage(message);



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
