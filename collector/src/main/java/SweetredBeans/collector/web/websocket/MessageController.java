package SweetredBeans.collector.web.websocket;

import SweetredBeans.collector.domain.user.User;
import SweetredBeans.collector.domain.user.UserRepository;
import SweetredBeans.collector.domain.websocket.Message;
import SweetredBeans.collector.domain.websocket.MessageRepository;
import SweetredBeans.collector.domain.websocket.MessageRoom;
import SweetredBeans.collector.domain.websocket.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/direct-message")
public class MessageController {

    private final MessageService messageService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @PostMapping
    public void createRoom(@RequestBody MessageRoom messageRoom, HttpServletResponse response) throws IOException {
        Boolean result = false;

        log.info("messageRoom.user_id= {}", messageRoom.getUser_id());
        log.info("messageRoom.transaction_id= {}", messageRoom.getTransaction_id());

        messageService.createRoom(messageRoom);

        result = true;
        HashMap<String, String> roomMap = new HashMap<>();
        roomMap.put("result", Boolean.toString(result));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(roomMap));

//        return messageService.createRoom(messageRoom);
    }

    @GetMapping
    public void findAllRoom(@RequestParam Long user_id, MessageRoom messageRoom, HttpServletResponse response) throws IOException {
        List<MessageRoom> userRoom = messageService.findUserRoom(user_id);
        HashMap<String, List<String>> roomMap = new HashMap<>();
        ArrayList roomIds = new ArrayList();

        Iterator<MessageRoom> iterator = userRoom.iterator();
        while(iterator.hasNext()) {
            MessageRoom room = iterator.next();
            Long roomId = room.getChat_room_id();
            log.info("roomId= {}", roomId);
            roomIds.add(roomId);
        }

        roomMap.put("room_id", roomIds);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(roomMap));
    }

    @GetMapping("/detail")
    public void getAllChatContext(@RequestParam Long room_id, HttpServletResponse response) throws IOException {
        List<Message> messages = messageRepository.findMessageByRoomId(room_id);
        ArrayList<HashMap<String, String>> messagesList = new ArrayList<>();

        Iterator<Message> iterator = messages.iterator();
        while(iterator.hasNext()) {
            HashMap<String, String> messageMap = new HashMap<>();
            Message m = iterator.next();
            User userById = userRepository.findById(m.getUser_id());
            String content = m.getContent();
            String userId = Long.toString(m.getUser_id());
            String writtenDateFormat = m.getWritten_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String imageUrl = m.getImage_url();
            String nickname = userById.getNickname();
            String profileUrl = userById.getProfile_url();
            String reliability = Integer.toString(userById.getReliability());

            log.info("========================================");
            log.info("content= {}", content);
            log.info("user_id= {}", userId);
            log.info("written_date= {}", writtenDateFormat);
            log.info("image_url= {}", imageUrl);
            log.info("nickname= {}", nickname);
            log.info("profile_url= {}", profileUrl);
            log.info("reliability= {}", reliability);
            log.info("========================================");

            messageMap.put("content", content);
            messageMap.put("user_id", userId);
            messageMap.put("written_date", writtenDateFormat);
            messageMap.put("image_url", imageUrl);
            messageMap.put("nickname", nickname);
            messageMap.put("profile_url", profileUrl);
            messageMap.put("reliability", reliability);

            messagesList.add(messageMap);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(messagesList));
    }
}
