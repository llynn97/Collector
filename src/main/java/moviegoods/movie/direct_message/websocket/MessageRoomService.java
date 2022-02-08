package moviegoods.movie.direct_message.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.direct_message.websocket.repository.ChatRoomRepository;
import moviegoods.movie.direct_message.websocket.repository.MessageRepository;
import moviegoods.movie.domain.Chat_Room;
import moviegoods.movie.domain.Content_detail;
import moviegoods.movie.domain.Message;
import moviegoods.movie.domain.User;
import moviegoods.movie.information_share.InformationRepository.InformationShareUserRepository;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageService messageService;
    private ChatRoomRepository chatRoomRepository;
    private InformationShareUserRepository informationShareUserRepository;
    private MessageRepository messageRepository;

    public void handleActions(WebSocketSession session, DirectMessage message, MessageService messageService, Long chat_room_id) {
       // if(message.getMessageType().equals(Message.MessageType.ENTER)) {

        //}
        sendMessage(message, messageService,chat_room_id);
    }

    public <T> void sendMessage(T message, MessageService messageService,Long chat_room_id) {
        DirectMessage msg = (DirectMessage) message;
        log.info("msg.userId= {}", msg.getUser_id());
        log.info("msg.roomId= {}", msg.getChat_room_id());
        log.info("msg.content= {}", msg.getContent());

        log.info("msg.url= {}", msg.getImage_url());
        Message message1=new Message();
        User user= informationShareUserRepository.findById(msg.getUser_id()).get();
        Chat_Room chat_room=chatRoomRepository.findById(msg.getChat_room_id()).get();
        Content_detail content_detail=new Content_detail();
        content_detail.setWritten_date(LocalDateTime.now());
        if(msg.getImage_url()==null){
            content_detail.setContent(msg.getContent());
        }else {

            message1.setImage_url(msg.getImage_url());
        }
        content_detail.setMessage(message1);
        message1.setContent_detail(content_detail);
        user.getMessages().add(message1);
        message1.setUser(user);
        chat_room.getMessages().add(message1);
        message1.setChat_room(chat_room);
        messageRepository.save(message1);


        Set<WebSocketSession> sessions=chatRoomRepository.findById(chat_room_id).get().getSessions();
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));
    }
}
