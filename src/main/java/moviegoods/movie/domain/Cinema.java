package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Entity(name="cinema")
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinema_id;

    @NotBlank
    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String area;

    @Column(length = 50)
    private String branch;

    @OneToOne(mappedBy = "cinema")
    private Post post;

    @OneToOne(mappedBy = "cinema")
    private Event event;

    public Cinema(){


    }


    @Builder
    public Cinema(String name, String area,String branch){
        this.name=name;
        this.area=area;
        this.branch=branch;
    }
}
