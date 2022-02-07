package SweetredBeans.collector.domain.websocket;

import SweetredBeans.collector.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final ObjectMapper objectMapper;
    private Map<Long, MessageRoom> msgRooms;
    private static long roomIdSequence = 0L;
    private static long messageSequence = 0L;
    private final MessageRepository messageRepository;

    @PostConstruct
    private void init() {
        msgRooms = new LinkedHashMap<>();
    }

    public List<MessageRoom> findAllRoom() {
        return new ArrayList<>(msgRooms.values());
    }

    public List<MessageRoom> findUserRoom(Long userId) {
        return findAllRoom().stream()
                .filter(u -> u.getUser_id().equals(userId))
                .collect(Collectors.toList());
    }

    public MessageRoom findById(Long roodId) {
        return msgRooms.get(roodId);
    }

    public MessageRoom createRoom(MessageRoom messageRoom){
        long seq = ++roomIdSequence;
        messageRoom.setChat_room_id(seq);
        msgRooms.put(seq, messageRoom);
        return MessageRoom.builder().chat_room_id(seq)
                .user_id(messageRoom.getUser_id())
                .transaction_id(messageRoom.getTransaction_id()).build();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            Message msg = (Message) message;
            msg.setMessage_id(++messageSequence);
            msg.setWritten_date(LocalDateTime.now());
            log.info("msg.msgType= {}", msg.getMessageType());
            log.info("msg.userId= {}", msg.getUser_id());
            log.info("msg.roomId= {}", msg.getChat_room_id());
            log.info("msg.content= {}", msg.getContent());
            log.info("msg.writtenTime= {}", msg.getWritten_date());
            log.info("msg.url= {}", msg.getImage_url());
            if(messageRepository.isDup(msg.getUser_id(), msg.getWritten_date())) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString((T) msg)));
            }
            else {
                messageRepository.save(msg);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString((T) msg)));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
