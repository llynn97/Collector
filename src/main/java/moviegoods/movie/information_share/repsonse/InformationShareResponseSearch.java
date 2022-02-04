package moviegoods.movie.information_share.repsonse;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
public class InformationShareResponseSearch {

    private Long post_id;   //post
    private String title;  //post
    private String nickname; //user
    private LocalDateTime written_date;  //content-detail
    private Long view;  //post

}
