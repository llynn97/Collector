package moviegoods.movie.domain.dto.directMessage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Getter
@Setter
public class DirectMessage {


    private String image_url;
    private String content;
    private Long chat_room_id;
    private String nickname;
    private Long user_id;
    private LocalDateTime written_date;




}
