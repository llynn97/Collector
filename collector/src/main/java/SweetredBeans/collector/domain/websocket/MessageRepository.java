package SweetredBeans.collector.domain.websocket;

import SweetredBeans.collector.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MessageRepository {

    private static Map<Long, Message> messageStore = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Message save(Message message) {
        message.setMessage_id(++sequence);
        messageStore.put(message.getMessage_id(), message);
        return message;
    }

    public List<Message> findAll() {
        return new ArrayList<>(messageStore.values());
    }

    public List<Message> findMessageByRoomId(Long roomId) {
        return findAll().stream()
                .filter(m -> m.getChat_room_id().equals(roomId))
                .collect(Collectors.toList());
    }

    public Boolean isDuplicate(Long userId, LocalDateTime written_date) {
        for(int i=0; i<messageStore.size(); i++) {
            if(messageStore.get(i).getUser_id().equals(userId)) {
                if(messageStore.get(i).getWritten_date().equals(written_date)) {
                    return true;
                }
            }
        }
        return false;
    }

}
