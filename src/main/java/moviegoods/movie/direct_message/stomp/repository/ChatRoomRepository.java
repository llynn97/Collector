package moviegoods.movie.direct_message.stomp.repository;

import moviegoods.movie.domain.Chat_Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<Chat_Room,Long> {
}
