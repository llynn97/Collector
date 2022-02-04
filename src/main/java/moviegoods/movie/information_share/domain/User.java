package moviegoods.movie.information_share.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name="user")
@AllArgsConstructor

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String nickname;

    @OneToOne(mappedBy ="user" )
    private Post post;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments=new ArrayList<>();

    public User(){

    }
}
