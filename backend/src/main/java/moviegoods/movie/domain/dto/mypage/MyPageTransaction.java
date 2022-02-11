package moviegoods.movie.domain.dto.mypage;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyPageTransaction {

    Long transaction_id;
    String content;
    LocalDateTime written_date;

    public MyPageTransaction(Long transaction_id, String content, LocalDateTime written_date){
        this.transaction_id=transaction_id;
        this.content=content;
        this.written_date=written_date;

    }
}
