package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformationShareUserRepository extends JpaRepository<User,Long> {



}
