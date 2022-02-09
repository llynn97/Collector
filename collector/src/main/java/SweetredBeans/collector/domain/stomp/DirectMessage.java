package SweetredBeans.collector.domain.stomp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DirectMessage {
    public enum MessageType {
        ENTER, COMM
    }

    private Long message_id;

    private MessageType messageType;

    private Long chat_room_id;

    private Long user_id;
    private String nickname;

    private String content;
    private LocalDateTime written_date;

    private String image_url;
}