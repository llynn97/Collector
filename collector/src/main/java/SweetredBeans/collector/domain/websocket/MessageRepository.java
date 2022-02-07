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
    //private static long sequence = 0L;

    public Message save(Message message) {
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

    /**
     * @return 중복 메시지면 return true 아니면 false
     */
    public boolean isDup (Long userId, LocalDateTime writtenDateTime) {
        List<Message> all = findAll();
        for (Message message : all) {
            if(message.getUser_id().equals(userId)) {
                if(message.getWritten_date().isEqual(writtenDateTime)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public Message delete(Message message) {
        messageStore.remove(message.getMessage_id());
        return message;
    }

}
