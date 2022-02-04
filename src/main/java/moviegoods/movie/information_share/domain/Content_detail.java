package moviegoods.movie.information_share.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity(name="content_detail")
public class Content_detail {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long content_detail_id;
    private String content;

    @OneToOne(mappedBy ="content_detail")
    private Post post;

    @DateTimeFormat
    private LocalDateTime written_date;

    @OneToOne(mappedBy = "content_detail")
    private Comment comment;

    public Content_detail(){

    }


}
