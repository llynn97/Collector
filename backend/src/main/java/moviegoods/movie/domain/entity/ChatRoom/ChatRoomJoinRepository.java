package moviegoods.movie.domain.entity.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomJoinRepository extends JpaRepository<Chat_Room_Join,Long> {
}
