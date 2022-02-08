package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity(name="message")
public class Message {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long message_id;

    private String image_url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_detail_id")
    private Content_detail content_detail;


    @ManyToOne
    @JoinColumn(name="chat_room_id")
    private Chat_Room chat_room;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Message (){

    }
}
