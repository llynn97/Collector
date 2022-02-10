package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomRequestDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomResponseDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageDetailResponseDto;
import moviegoods.movie.service.MessageRoomService;
import moviegoods.movie.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/direct-message")
public class ChatRoomController {

    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<DirectMessageCreateRoomResponseDto> creat(@RequestBody DirectMessageCreateRoomRequestDto requestDto){
        DirectMessageCreateRoomResponseDto responseDto = messageRoomService.createRoom(requestDto);
        ResponseEntity<DirectMessageCreateRoomResponseDto> result = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return result;
    }

    @GetMapping
    public ResponseEntity<List<Long>> directMessageList (@RequestParam Long user_id) {
        List<Long> roomsList = messageRoomService.findMessageRooms(user_id);
        ResponseEntity<List<Long>> result = new ResponseEntity<>(roomsList, HttpStatus.OK);
        return result;
    }

    @PostMapping("/detail")
    public ResponseEntity<List<DirectMessageDetailResponseDto>> detail (@RequestBody Long room_id) {
        List<DirectMessageDetailResponseDto> messagesList = messageService.show(room_id);

        ResponseEntity<List<DirectMessageDetailResponseDto>> result = new ResponseEntity<>(messagesList, HttpStatus.OK);
        return result;
    }

}
