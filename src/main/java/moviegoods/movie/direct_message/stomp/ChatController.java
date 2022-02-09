package moviegoods.movie.direct_message.stomp;


import lombok.RequiredArgsConstructor;
import moviegoods.movie.direct_message.stomp.repository.ChatRoomRepository;
import moviegoods.movie.direct_message.stomp.repository.MessageRepository;

import moviegoods.movie.domain.Chat_Room;
import moviegoods.movie.domain.Content_detail;
import moviegoods.movie.domain.Message;
import moviegoods.movie.domain.User;
import moviegoods.movie.information_share.InformationRepository.InformationShareUserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final InformationShareUserRepository informationShareUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

     @MessageMapping("/chat/message")
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

             message1.setImage_url(message.getImage_url());
         }

         content_detail.setMessage(message1);
         message1.setContent_detail(content_detail);
         user.getMessages().add(message1);
         message1.setUser(user);
         chat_room.getMessages().add(message1);
         message1.setChat_room(chat_room);
         messageRepository.save(message1);

         messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChat_room_id());


     }

}
