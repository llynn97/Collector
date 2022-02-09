package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name="user")
@AllArgsConstructor

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank
    @Column(length=30)
    private String nickname;


    @NotBlank
    @Column(length = 100)
    private String email;


    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Transaction> transactions=new ArrayList<>();

    private int reliability;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Report> reports=new ArrayList<>();

    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();

    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL)
    private List<Message> messages=new ArrayList<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Chat_Room_Join> chat_room_joins=new ArrayList<>();

    public User(){

    }
}
