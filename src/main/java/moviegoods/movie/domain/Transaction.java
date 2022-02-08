package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import moviegoods.movie.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity(name="transaction")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(mappedBy = "transaction" ,cascade = CascadeType.ALL)
    private List<Chat_Room> chat_rooms=new ArrayList<>();

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Report> reports=new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="content_detail_id")
    private Content_detail content_detail;

    @Enumerated(EnumType.STRING)
    private Status status;

}
