package moviegoods.movie.domain.dto.directMessage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageCreateRoomResponseDto {

    private boolean result;
    private Long chat_room_id;
    private Long user_id;
    private Long writer_id;
}
