package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "like_basket")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Like_Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_basket_id;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
