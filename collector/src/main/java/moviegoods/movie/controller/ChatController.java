package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import moviegoods.movie.domain.entity.Message.DirectMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

     @MessageMapping("/chat/messgae")
    public void message(DirectMessage message){
         if(message.getMessageType().equals(DirectMessage.MessageType.ENTER)){
           message.setContent(message.getNickname()+"입장");
           messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChat_room_id());
         }

     }

}
