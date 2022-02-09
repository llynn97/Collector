package moviegoods.movie.domain.dto.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsSearchResponseDto {
    private Long user_id;
    private String content;
    private String status;
    private Long transaction_id;
    private Long reliability;
    private LocalDateTime written_date;
    private Boolean is_mine;
    private Boolean is_like;


}
