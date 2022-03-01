package moviegoods.movie.controller;


import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;

import moviegoods.movie.domain.dto.directMessage.*;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.Message.MessageRepository;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.FireBaseService;
import moviegoods.movie.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/direct-message")
public class ChatController {
    private final UserRepository informationShareUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ChatService messageService;
    private final FireBaseService fireBaseService;

    @MessageMapping("/chat/message")
    @SendToUser("/sub/chat/room/2")
    public DirectMessage message(DirectMessage message) throws IOException, FirebaseAuthException {
        // if(message.getMessageType().equals(DirectMessage.MessageType.ENTER)) {
        //   message.setContent(message.getNickname() + "입장");

        //}
        messageService.saveMessage(message);


//        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChat_room_id());
        return message;


    }


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
