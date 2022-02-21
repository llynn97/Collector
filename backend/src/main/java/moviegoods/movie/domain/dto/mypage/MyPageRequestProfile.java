package moviegoods.movie.domain.dto.mypage;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MyPageRequestProfile {
    private MultipartFile profile_image;
<<<<<<< HEAD
=======
    private String nickname;
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
    private Long user_id;

}
