package moviegoods.movie.direct_message.stomp.repository;

import moviegoods.movie.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
