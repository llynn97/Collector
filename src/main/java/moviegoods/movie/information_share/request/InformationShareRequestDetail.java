package moviegoods.movie.information_share.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InformationShareRequestDetail {

   private Long post_id;
   private Long user_id;
}
