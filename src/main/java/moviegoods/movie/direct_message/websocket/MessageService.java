package moviegoods.movie.direct_message.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.direct_message.websocket.repository.ChatRoomRepository;
import moviegoods.movie.domain.Chat_Room;
import moviegoods.movie.domain.Content_detail;
import moviegoods.movie.domain.Message;
import moviegoods.movie.domain.User;
import moviegoods.movie.information_share.InformationRepository.InformationShareUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private  final MessageRoomService messageRoomService;
    private final ObjectMapper objectMapper;
    private final InformationShareUserRepository informationShareUserRepository;



    public Chat_Room createRoom(WebSocketSession session){ //채팅방 만들기, buyer_id, user_id 들어옴 , 세션 chat_room에 넣어주기
       Chat_Room chat_room=new Chat_Room();
       chat_room.getSessions().add(session);
       //세션 저장 -> user_id는 자동으로 저장해줘야함
        //프론트엔드에 id랑 nickname 줘야함

       return chat_room;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            DirectMessage msg = (DirectMessage) message;




            session.sendMessage(new TextMessage(objectMapper.writeValueAsString((T) message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


}
