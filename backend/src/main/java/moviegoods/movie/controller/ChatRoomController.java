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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/direct-message")
public class ChatRoomController {

    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<DirectMessageCreateRoomResponseDto> create(@RequestBody DirectMessageCreateRoomRequestDto requestDto){
        DirectMessageCreateRoomResponseDto responseDto = messageRoomService.createRoom(requestDto);
        ResponseEntity<DirectMessageCreateRoomResponseDto> result = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return result;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Long>>> directMessageList (@RequestParam Long user_id) {
        List<Long> roomsList = messageRoomService.findMessageRooms(user_id);
        Map<String, List<Long>> roomsListJson = new HashMap<>();
        roomsListJson.put("room_id", roomsList);
        ResponseEntity<Map<String, List<Long>>> result = new ResponseEntity<>(roomsListJson, HttpStatus.OK);
        return result;
    }

    @GetMapping("/detail")
    public ResponseEntity<Map<String, List<DirectMessageDetailResponseDto>>> detail (@RequestParam Long room_id) {
        List<DirectMessageDetailResponseDto> messagesList = messageService.show(room_id);
        Map<String, List<DirectMessageDetailResponseDto>> messagesListJson = new HashMap<>();

        messagesListJson.put("message", messagesList);

        ResponseEntity<Map<String, List<DirectMessageDetailResponseDto>>> result = new ResponseEntity<>(messagesListJson, HttpStatus.OK);
        return result;
    }

}
