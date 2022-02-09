package moviegoods.movie.direct_message.stomp;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.direct_message.request.DirectMessageRequestCreateRoom;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/direct-message")
public class ChatRoomController {

    @PostMapping
    public void rooms(@RequestBody DirectMessageRequestCreateRoom dmrcr, HttpServletResponse response) throws IOException {
        Boolean result = false;

        log.info("messageRoom.user_id= {}", dmrcr.getUser_id());
        log.info("messageRoom.transaction_id= {}", dmrcr.getTransaction_id());


    }
}
