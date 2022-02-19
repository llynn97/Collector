package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomRequestDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomResponseDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageDetailResponseDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageListResponseDto;
import moviegoods.movie.service.ChatRoomService;
import moviegoods.movie.service.ChatService;
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

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<DirectMessageCreateRoomResponseDto> create(@RequestBody DirectMessageCreateRoomRequestDto requestDto){
        DirectMessageCreateRoomResponseDto responseDto = chatRoomService.createRoom(requestDto);
        ResponseEntity<DirectMessageCreateRoomResponseDto> result = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return result;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<DirectMessageListResponseDto>>> directMessageList (@RequestParam Long user_id) {
        List<DirectMessageListResponseDto> roomsList = chatRoomService.findMessageRooms(user_id);
        Map<String, List<DirectMessageListResponseDto>> roomsListJson = new HashMap<>();
        roomsListJson.put("room_id", roomsList);
        ResponseEntity<Map<String, List<DirectMessageListResponseDto>>> result = new ResponseEntity<>(roomsListJson, HttpStatus.OK);
        return result;
    }

    @GetMapping("/detail")
    public ResponseEntity<Map<String, List<DirectMessageDetailResponseDto>>> detail (@RequestParam Long room_id) {
        List<DirectMessageDetailResponseDto> messagesList = chatService.show(room_id);
        Map<String, List<DirectMessageDetailResponseDto>> messagesListJson = new HashMap<>();

        messagesListJson.put("message", messagesList);

        ResponseEntity<Map<String, List<DirectMessageDetailResponseDto>>> result = new ResponseEntity<>(messagesListJson, HttpStatus.OK);
        return result;
    }

}
