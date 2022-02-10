package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.directMessage.DirectMessageDetailResponseDto;
import moviegoods.movie.domain.entity.ChatRoom.ChatRoomRepository;
import moviegoods.movie.domain.entity.ChatRoom.Chat_Room;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Message.Message;
import moviegoods.movie.domain.entity.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;

    public List<DirectMessageDetailResponseDto> show(Long room_id) {
        List<DirectMessageDetailResponseDto> messagesList = new ArrayList<>();
        Optional<Chat_Room> chatRoom = chatRoomRepository.findById(room_id);
        Chat_Room findedRoom = chatRoom.get();

        List<Message> messages = findedRoom.getMessages();
        for (Message message : messages) {
            Content_Detail content_detail = message.getContent_detail();
            String content = content_detail.getContent();
            LocalDateTime written_date = content_detail.getWritten_date();
            String image_url = message.getImage_url();

            User user = message.getUser();
            Long user_id = user.getUser_id();
            String nickname = user.getNickname();
            String profile_url = user.getProfile_url();
            Long reliability = user.getReliability();

            messagesList.add(new DirectMessageDetailResponseDto(content, written_date, image_url, user_id, nickname, profile_url, reliability));
        }
        return messagesList;
    }
}
