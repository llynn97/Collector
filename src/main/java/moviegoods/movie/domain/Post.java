package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "post")
@Data
@AllArgsConstructor

public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_detail_id")
    private Content_detail content_detail;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cinema_id")
    private Cinema cinema;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    @NotNull
    private Long views=0L;

    @NotBlank
    @Column(length = 100)
    private String title;

    @NotBlank
    @Column(length = 50)
    private String category;
    public Post(){

    }

   @Builder
    public Post( Content_detail content_detail, Cinema cinema,User user, String image_url, String title){
       this.content_detail=content_detail;

       this.cinema=cinema;
       this.image_url=image_url;
       this.title=title;
       this.user=user;
    }
}
