package moviegoods.movie.information_share.request;


import lombok.Data;

@Data
public class InformationShareRequestSaveComment {
    Long post_id;
    Long user_id;
    String content;
}
