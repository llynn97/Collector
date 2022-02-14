package moviegoods.movie.domain.dto.directMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageCreateRoomResponseDto {

    private boolean result;
    private Long chat_room_id;
    private Long demand_user_id;
    private Long writer_id;
}
