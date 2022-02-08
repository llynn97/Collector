package moviegoods.movie.direct_message.websocket;

import lombok.Getter;
import lombok.Setter;
import moviegoods.movie.domain.Chat_Room;
import moviegoods.movie.domain.Content_detail;
import moviegoods.movie.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;


@Getter
@Setter
public class DirectMessage {



  /*  public enum MessageType {
        ENTER, COMM
    }*/

   // private MessageType messageType;


    private String image_url;
    private String content;
    private String nickname;
    private Long user_id;
    private Long chat_room_id;
    //private LocalDateTime written_date;


    //private Content_detail content_detail;
  // private User user;
  //  private Chat_Room chat_room;



}
