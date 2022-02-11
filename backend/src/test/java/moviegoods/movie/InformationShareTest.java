package moviegoods.movie;


import moviegoods.movie.domain.entity.Cinema.CinemaRepository;
import moviegoods.movie.domain.entity.Content_Detail.ContentDetailRepository;
import moviegoods.movie.domain.entity.Post.PostRepository;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationShareTest {
    @Autowired
    ContentDetailRepository informationShareContent_detailRepository;
    @Autowired
    UserRepository informationShareUserRepository;
    @Autowired
    PostRepository informationSharePostRepository;
    @Autowired
    CinemaRepository informationShareCinemaRepository;

    //@Autowired
   // InformationShareService informationShareService;


    @Test
    public void testShare(){
        User user=new User();
       // informationShareService.saveUser(user);


    }

}
