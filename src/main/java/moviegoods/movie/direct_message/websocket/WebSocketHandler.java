package moviegoods.movie.direct_message.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.direct_message.websocket.DirectMessage;
import moviegoods.movie.direct_message.websocket.MessageService;
import moviegoods.movie.direct_message.websocket.repository.ChatRoomRepository;
import moviegoods.movie.domain.Chat_Room;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



@Slf4j
@Component
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final MessageRoomService messageRoomService;
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload= {}", payload);

        DirectMessage msg = objectMapper.readValue(payload, DirectMessage.class);
        // Chat_Room room = chatRoomRepository.findById(msg.getChat_room_id()).get();
        messageRoomService.handleActions(session, msg, messageService,msg.getChat_room_id());
    }
}
