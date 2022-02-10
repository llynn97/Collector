package moviegoods.movie.domain.entity.Content_Detail;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import moviegoods.movie.domain.entity.Comment;
import moviegoods.movie.domain.entity.Message.Message;
import moviegoods.movie.domain.entity.Post.Post;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.Transaction.Transaction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "content_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content_Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long content_detail_id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @DateTimeFormat
    private LocalDateTime written_date;

    @OneToOne(mappedBy = "content_detail",cascade = CascadeType.REMOVE)
    private Post post;

    @OneToOne(mappedBy = "content_detail",cascade = CascadeType.REMOVE)
    private Message message;

    @OneToOne(mappedBy = "content_detail",cascade = CascadeType.REMOVE)
    private Comment comment;

    @OneToOne(mappedBy = "content_detail", cascade = CascadeType.REMOVE)
    private Transaction transaction;

    @OneToOne(mappedBy = "content_detail",cascade = CascadeType.REMOVE)
    private Report report;

}
