package SweetredBeans.collector.web.stomp;

import SweetredBeans.collector.domain.entity.Chat_Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<Chat_Room, Long> {
}
