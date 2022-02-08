package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity(name="chat_room")
public class Chat_Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_room_id;


    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Chat_Room_Join > chat_room_joins=new ArrayList<>();



    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Message>messages=new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "sessions",joinColumns = @JoinColumn(name="chat_room_id"))
    private Set<WebSocketSession> sessions=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Chat_Room(){

    }

}
