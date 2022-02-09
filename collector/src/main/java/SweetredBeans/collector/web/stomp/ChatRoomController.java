package SweetredBeans.collector.web.stomp;


import SweetredBeans.collector.domain.entity.Chat_Room;
import SweetredBeans.collector.domain.entity.Transaction;
import SweetredBeans.collector.domain.stomp.DirectMessageTransactionRepository;
import SweetredBeans.collector.web.request.DirectMessageRequestCreateRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/direct-message")
public class ChatRoomController {

    private final MessageRoomService messageRoomService;
    private final DirectMessageTransactionRepository transactionRepository;

    @PostMapping
    public void creat(DirectMessageRequestCreateRoom dmrcr, HttpServletResponse response) throws IOException {
        Boolean result = false;

        log.info("messageRoom.user_id= {}", dmrcr.getUser_id());
        log.info("messageRoom.transaction_id= {}", dmrcr.getTransaction_id());

        Chat_Room createdRoom = messageRoomService.createRoom(dmrcr);
        Transaction relatedTransaction = transactionRepository.getById(dmrcr.getTransaction_id());

        result = true;
        HashMap<String, String> roomMap = new HashMap<>();
        roomMap.put("result", Boolean.toString(result));
        roomMap.put("user_id", Long.toString(dmrcr.getUser_id()));
        roomMap.put("chat_room_id", Long.toString(createdRoom.getChat_room_id()));
        roomMap.put("writer_id", Long.toString(relatedTransaction.getUser().getUser_id()));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(roomMap));

    }
}
