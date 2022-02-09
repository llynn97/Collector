package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.domain.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformationShareCinemaRepository extends JpaRepository<Cinema,Long> {






}
