package moviegoods.movie.direct_message.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectMessageRequestCreateRoom {

    Long user_id;
    Long transaction_id;

}
