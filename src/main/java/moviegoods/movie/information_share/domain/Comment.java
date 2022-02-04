package moviegoods.movie.information_share.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="comment")
public class Comment {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_detail_id")
    private Content_detail content_detail;
}
