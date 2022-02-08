package moviegoods.movie.direct_message.messageRepository;

import moviegoods.movie.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageTransactionRepository extends JpaRepository<Transaction,Long> {
}
