package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationShareCommentRepository extends JpaRepository<Comment,Long> {
}
