package moviegoods.movie.domain.dto.directMessage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageCreateRoomRequestDto {

    Long user_id;
    Long transaction_id;
}
