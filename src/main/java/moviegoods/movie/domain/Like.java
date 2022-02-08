package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity(name="like")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    private Event event;

    private User user;

    private Transaction transaction;
}
