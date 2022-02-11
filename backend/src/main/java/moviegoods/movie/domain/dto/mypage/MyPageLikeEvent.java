package moviegoods.movie.domain.dto.mypage;


import lombok.Data;

@Data
public class MyPageLikeEvent {

    // 좋아요한 이벤트
    private Long event_id;
    private String event_title;
    private String thumbnail_url;

    public MyPageLikeEvent(Long event_id, String event_title, String thumbnail_url){
        this.event_id=event_id;
        this.event_title=event_title;
        this.thumbnail_url=thumbnail_url;
    }
}
