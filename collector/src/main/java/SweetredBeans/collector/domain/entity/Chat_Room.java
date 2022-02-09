package SweetredBeans.collector.domain.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat_Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_room_id;

    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Chat_Room_Join > chat_room_joins=new ArrayList<>();

    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Message>messages=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;


}

