package SweetredBeans.collector.web.stomp;

import SweetredBeans.collector.domain.entity.Chat_Room;
import SweetredBeans.collector.domain.entity.Chat_Room_Join;
import SweetredBeans.collector.domain.entity.Transaction;
import SweetredBeans.collector.domain.entity.User;
import SweetredBeans.collector.domain.user.UserRepository;
import SweetredBeans.collector.domain.stomp.DirectMessageTransactionRepository;
import SweetredBeans.collector.web.request.DirectMessageRequestCreateRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final DirectMessageTransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public Chat_Room createRoom(DirectMessageRequestCreateRoom dmrcr){

        Chat_Room chat_room = new Chat_Room();
        User user = userRepository.getById(dmrcr.getUser_id());
        Transaction relatedTransaction = transactionRepository.getById(dmrcr.getTransaction_id());
        chat_room.setTransaction(relatedTransaction);

        Chat_Room savedMessageRoom = messageRoomRepository.save(chat_room);

        Chat_Room_Join chat_room_join = new Chat_Room_Join();
        chat_room_join.setChat_room(savedMessageRoom);
        chat_room_join.setUser(user);
        List<Chat_Room_Join> user_chat_room_join = user.getChat_room_joins();
        user_chat_room_join.add(chat_room_join);

        return savedMessageRoom;
    }
}
