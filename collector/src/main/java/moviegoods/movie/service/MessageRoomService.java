package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomRequestDto;
import moviegoods.movie.domain.dto.directMessage.DirectMessageCreateRoomResponseDto;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomJoinRepository;
import moviegoods.movie.domain.entity.ChatRoom.Chat_Room;
import moviegoods.movie.domain.entity.ChatRoom.Chat_Room_Join;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.Transaction.Transaction;
import moviegoods.movie.domain.entity.Transaction.TransactionRepository;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DirectMessageCreateRoomResponseDto createRoom(DirectMessageCreateRoomRequestDto requestDto){
        Chat_Room chat_room = new Chat_Room();

        Transaction relatedTransaction = transactionRepository.getById(requestDto.getTransaction_id());
        Long writer_id = relatedTransaction.getUser().getUser_id();
        User user = userRepository.getById(requestDto.getUser_id());
        User writerUser = userRepository.getById(writer_id);
        chat_room.setTransaction(relatedTransaction);
        Chat_Room savedMessageRoom = chatRoomRepository.save(chat_room);

        Chat_Room_Join chat_room_join = new Chat_Room_Join();
        chat_room_join.setChat_room(savedMessageRoom);
        chat_room_join.setUser(user);
        Chat_Room_Join chat_room_join_Writer = new Chat_Room_Join();
        chat_room_join_Writer.setChat_room(savedMessageRoom);
        chat_room_join_Writer.setUser(writerUser);
        chatRoomJoinRepository.save(chat_room_join);
        chatRoomJoinRepository.save(chat_room_join_Writer);

        List<Chat_Room_Join> user_chat_room_join = user.getChat_room_joins();
        user_chat_room_join.add(chat_room_join);
        List<Chat_Room_Join> writer_chat_room_joins = writerUser.getChat_room_joins();
        writer_chat_room_joins.add(chat_room_join_Writer);

        DirectMessageCreateRoomResponseDto responseDto =
                new DirectMessageCreateRoomResponseDto(true,
                        savedMessageRoom.getChat_room_id(),
                        user.getUser_id(),
                        writer_id);

        return responseDto;
    }

    public List<Long> findMessageRooms(Long user_id) {
        List<Long> roomsList = new ArrayList<>();
        Optional<User> user = userRepository.findById(user_id);
        User findedUser = user.get();
        List<Chat_Room_Join> chat_room_joins = findedUser.getChat_room_joins();
        for (Chat_Room_Join chat_room_join : chat_room_joins) {
            Chat_Room chat_room = chat_room_join.getChat_room();
            Long chat_room_id = chat_room.getChat_room_id();
            roomsList.add(chat_room_id);
        }
        return roomsList;
    }
}
