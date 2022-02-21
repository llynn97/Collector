package moviegoods.movie.domain.entity.Event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moviegoods.movie.domain.entity.Cinema.Cinema;
import moviegoods.movie.domain.entity.Like_Basket.Like_Basket;

import javax.persistence.*;

@Entity(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;

    @NotBlank
    @Column(length = 100)
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String thumbnail_url;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String detail_image_url;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String link_url;

    @Temporal(TemporalType.DATE)
    private java.util.Date start_date;

    @Temporal(TemporalType.DATE)
    private java.util.Date end_date;

    private Long like_count = 0L;

    @OneToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

<<<<<<< HEAD
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
=======
    @OneToOne(mappedBy = "event", orphanRemoval = true)
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
    private Like_Basket like;

}
