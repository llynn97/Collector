package moviegoods.movie.information_share.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity(name="cinema")
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinema_id;
    private String name;
    private String area=null;
    private String branch=null;

    @OneToOne(mappedBy = "cinema")
    private Post post;

    public Cinema(){


    }


    @Builder
    public Cinema(String name, String area,String branch){
        this.name=name;
        this.area=area;
        this.branch=branch;
    }
}
