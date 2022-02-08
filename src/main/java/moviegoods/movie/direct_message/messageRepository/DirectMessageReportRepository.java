package moviegoods.movie.direct_message.messageRepository;

import moviegoods.movie.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageReportRepository extends JpaRepository<Report,Long> {
}
