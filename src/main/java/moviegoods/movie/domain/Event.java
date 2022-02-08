package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity(name="event")
public class Event {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;
}
