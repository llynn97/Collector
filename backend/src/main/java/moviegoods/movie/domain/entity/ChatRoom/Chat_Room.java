package moviegoods.movie.domain.entity.ChatRoom;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moviegoods.movie.domain.entity.Message.Message;
import moviegoods.movie.domain.entity.Transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat_Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_room_id;


    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Chat_Room_Join > chat_room_joins=new ArrayList<>();



    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Message>messages=new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Chat_Room(){

    }
    ;

}

