package moviegoods.movie.domain.dto.informationShare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InformationShareSearchResponseDto {
    private Long post_id;   //post
    private String title;  //post
    private String nickname; //user
    private LocalDateTime written_date;  //content-detail
    private Long views;  //post

}