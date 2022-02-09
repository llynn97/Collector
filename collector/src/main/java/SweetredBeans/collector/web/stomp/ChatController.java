package SweetredBeans.collector.web.stomp;


import SweetredBeans.collector.domain.stomp.DirectMessage;
import lombok.RequiredArgsConstructor;
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
