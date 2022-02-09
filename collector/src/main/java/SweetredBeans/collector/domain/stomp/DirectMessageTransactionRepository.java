package SweetredBeans.collector.domain.stomp;

import SweetredBeans.collector.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageTransactionRepository extends JpaRepository<Transaction,Long> {

}
