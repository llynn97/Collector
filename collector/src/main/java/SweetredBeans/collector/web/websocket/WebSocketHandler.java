package SweetredBeans.collector.web.websocket;

import SweetredBeans.collector.domain.websocket.Message;
import SweetredBeans.collector.domain.websocket.MessageRoom;
import SweetredBeans.collector.domain.websocket.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload= {}", payload);

        Message msg = objectMapper.readValue(payload, Message.class);
        MessageRoom room = messageService.findById(msg.getChat_room_id());
        room.handleActions(session, msg, messageService);
    }
}
