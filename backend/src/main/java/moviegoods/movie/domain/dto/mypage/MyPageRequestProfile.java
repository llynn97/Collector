package moviegoods.movie.domain.dto.mypage;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MyPageRequestProfile {
    private MultipartFile profile_image;
    private String nickname;
    private Long user_id;

}
