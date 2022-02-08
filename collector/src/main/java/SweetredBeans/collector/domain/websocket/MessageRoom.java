package SweetredBeans.collector.domain.websocket;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class MessageRoom {

    private Long chat_room_id;
    private Long user_id;
    private Long transaction_id;

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MessageRoom(Long chat_room_id, Long user_id, Long transaction_id) {
        this.chat_room_id = chat_room_id;
        this.user_id = user_id;
        this.transaction_id = transaction_id;
    }

    public void handleActions(WebSocketSession session, Message message, MessageService messageService) {
        if(message.getMessageType().equals(Message.MessageType.ENTER)) {
            sessions.add(session);
            message.setContent(message.getUser_id() + "님이 입장했습니다.");
        }
        sendMessage(message, messageService);
    }

    public <T> void sendMessage(T message, MessageService messageService) {
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));
    }

    @Override
    public String toString() {
        return "MessageRoom{" +
                "chat_rood_id=" + chat_room_id +
                ", user_id=" + user_id +
                ", transaction_id=" + transaction_id +
                '}';
    }
}