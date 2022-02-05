package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.information_share.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface InformationSharePostRepository extends JpaRepository<Post,Long> {



}
