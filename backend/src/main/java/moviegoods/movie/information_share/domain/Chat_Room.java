package moviegoods.movie.information_share.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat_Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_room_id;

    @OneToOne(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private Chat_Room_Join chat_room_join;

    @OneToMany(mappedBy = "chat_room",cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

}

