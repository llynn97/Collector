package moviegoods.movie.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareRequestWriteRequestDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareSearchRequestDto;
import moviegoods.movie.domain.dto.informationShare.InformationShareSearchResponseDto;
import moviegoods.movie.domain.dto.transactions.TransactionsSearchResponseDto;
import moviegoods.movie.domain.entity.Cinema.Cinema;
import moviegoods.movie.domain.entity.Content_Detail.ContentDetailRepository;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Post.Post;
import moviegoods.movie.domain.entity.Post.PostRepository;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformationShareService {

    private final UserRepository userRepository;
    private final ContentDetailService contentDetailService;
    private final ContentDetailRepository contentDetailRepository;
    private final CinemaService cinemaService;
    private final PostRepository postRepository;
    private final EntityManager em;

    public ResultResponseDto savePost(InformationShareRequestWriteRequestDto requestDto){
        Long user_id=requestDto.getUser_id();
        String title=requestDto.getTitle();
        String cinema_name=requestDto.getCinema_name();
        String cinema_area=requestDto.getCinema_area();
        String cinema_branch=requestDto.getCinema_branch();
        String content=requestDto.getContent();
        String image_url=requestDto.getImage_url();
        String category = "정보공유";
        Long views = 0L;

        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. user_id = {}"+ user_id));
        Content_Detail content_detail = contentDetailService.saveContentDetail(content);
        Cinema cinema = cinemaService.saveCinemaDetail(cinema_name, cinema_area, cinema_branch);
        Post saveEntity = Post.builder().category(category).user(user).content_detail(content_detail).views(views).cinema(cinema).image_url(image_url).title(title).build();

        ResultResponseDto resultResponseDto = new ResultResponseDto();
        postRepository.save(saveEntity);
        resultResponseDto.setResult(true);

        return resultResponseDto;
    }

    public List<InformationShareSearchResponseDto> search(InformationShareSearchRequestDto requestDto){
        List<InformationShareSearchResponseDto> searchList = new ArrayList<>();

        String search_word = requestDto.getSearch_word();
        String cinema_name = requestDto.getCinema_name();
        String cinema_area = requestDto.getCinema_area();
        String cinema_branch = requestDto.getCinema_branch();
        String sort_name = requestDto.getSort_name();
        String searchJpql="select p From post p join p.content_detail c ";
        List<Post> postList=em.createQuery(searchJpql,Post.class).getResultList();
        for (Post post : postList) {
            Long content_detail_id=post.getContent_detail().getContent_detail_id();
            Content_Detail content_detail= contentDetailRepository.findById(content_detail_id).orElseThrow(() -> new IllegalArgumentException("해당 상세 내역이 없습니다. content_detail_id = {}"+ content_detail_id));

            String nickname=post.getUser().getNickname();
            String title=post.getTitle();
            Long views=post.getViews();
            Long post_id=post.getPost_id();
            LocalDateTime written_date=content_detail.getWritten_date();
            searchList.add(new InformationShareSearchResponseDto(post_id,title,nickname,written_date,views));



        }
                return searchList;
    }


}