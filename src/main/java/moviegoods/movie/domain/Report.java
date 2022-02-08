package moviegoods.movie.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity(name="report")
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="content_detail_id")
    private Content_detail content_detail;

    public Report(){

    }

}
