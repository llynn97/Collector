package moviegoods.movie;


import moviegoods.movie.information_share.*;
import moviegoods.movie.information_share.InformationRepository.InformationShareCinemaRepository;
import moviegoods.movie.information_share.InformationRepository.InformationShareContent_detailRepository;
import moviegoods.movie.information_share.InformationRepository.InformationSharePostRepository;
import moviegoods.movie.information_share.InformationRepository.InformationShareUserRepository;
import moviegoods.movie.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationShareTest {
    @Autowired
    InformationShareContent_detailRepository informationShareContent_detailRepository;
    @Autowired
    InformationShareUserRepository informationShareUserRepository;
    @Autowired
    InformationSharePostRepository informationSharePostRepository;
    @Autowired
    InformationShareCinemaRepository informationShareCinemaRepository;

    @Autowired
    InformationShareService informationShareService;


    @Test
    public void testShare(){
        User user=new User();
        informationShareService.saveUser(user);


    }

}
