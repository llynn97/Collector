package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.information_share.domain.Cinema;
import moviegoods.movie.information_share.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformationShareCinemaRepository extends JpaRepository<Cinema,Long> {






}
