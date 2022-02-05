package moviegoods.movie.information_share.request;


import lombok.Data;

@Data
public class InformationShareRequestDeleteComment {

    private Long user_id;
    private Long comment_id;
}
