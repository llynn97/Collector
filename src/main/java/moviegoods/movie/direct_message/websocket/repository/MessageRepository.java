package moviegoods.movie.direct_message.websocket.repository;

import moviegoods.movie.direct_message.websocket.DirectMessage;
import moviegoods.movie.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
