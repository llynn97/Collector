package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity(name="chat_room_join")
public class Chat_Room_Join {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_room_join_id;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name="char_room_id")
    private Chat_Room chat_room;
}
