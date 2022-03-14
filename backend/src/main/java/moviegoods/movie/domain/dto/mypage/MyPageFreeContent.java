package moviegoods.movie.domain.dto.mypage;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MyPageFreeContent {

    Long post_id;
    String title;
    LocalDateTime written_date;
    String category;
    String content;

    public MyPageFreeContent(Long post_id, String title, LocalDateTime written_date, String category, String content){
        this.category=category;
        this.post_id=post_id;
        this.title=title;
        this.written_date=written_date;
        this.content=content;
    }
}
