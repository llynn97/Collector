package moviegoods.movie.information_share.InformationRepository;

import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationShareContent_detailRepository extends JpaRepository<Content_Detail,Long> {
}
