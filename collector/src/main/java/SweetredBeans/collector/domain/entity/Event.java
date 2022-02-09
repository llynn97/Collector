package SweetredBeans.collector.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Like_Basket like;

}
