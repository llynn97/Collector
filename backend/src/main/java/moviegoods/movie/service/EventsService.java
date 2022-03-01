package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.events.*;
import moviegoods.movie.domain.entity.Event.Event;
import moviegoods.movie.domain.entity.Event.EventRepository;
import moviegoods.movie.domain.entity.Like_Basket.LikeBasketRepository;
import moviegoods.movie.domain.entity.Like_Basket.Like_Basket;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventsService {

    private final EventRepository eventRepository;
    private final LikeBasketsService likeBasketsService;
    private final LikeBasketRepository likeBasketRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public List<EventsSearchResponseDto> search(EventsSearchRequestDto requestDto) throws ParseException {
        List<EventsSearchResponseDto> searchList = new ArrayList<>();

        String cinema_name = requestDto.getCinema_name();
        String search_word = requestDto.getSearch_word();
        String sort_criteria = requestDto.getSort_criteria(); // 최신순, 관심도순
        Boolean is_end = requestDto.getIs_end(); // 1(마감), 0(진행)
        Long user_id = requestDto.getUser_id();
        String linking_word = "where ";

        String searchJpql = "select e from event e join e.cinema c where ";
        Integer check = 0;
        if (search_word == null) {
            search_word = "";
        }

        String searchWordJpql = "";
        if (search_word != null) {
            check++;
            searchWordJpql = "e.title like '%" + search_word + "%' ";

            searchJpql += searchWordJpql;
        }

        // 영화사 이름
        if (cinema_name != null) {
            if(check >= 1) {
                linking_word = "and ";
            }
            check++;
            searchJpql += linking_word+"c.name = '"+cinema_name+"' ";
        }

        // 진행여부 true(마감), false(진행중)
        LocalDate now = LocalDate.now();
        String isEndJpql = "";
        if (is_end != null) {
            if (Objects.equals(is_end, true)) {
                isEndJpql = "e.end_date <= STR_TO_DATE(NOW(), '%Y-%m-%d') ";
            } else {
                isEndJpql = "e.end_date > STR_TO_DATE(NOW(), '%Y-%m-%d') ";
            }
            check++;
            if (check >= 1) {
                linking_word = "and ";
            }
            searchJpql += linking_word + isEndJpql;
        }

        // 정렬 기준(최신순) 기본 값 줘야
        if (Objects.equals(sort_criteria,"최신순")) {
            searchJpql += "order by e.start_date desc";
        }
        if (Objects.equals(sort_criteria,"관심도순")) {
            searchJpql += "order by e.like_count desc";
        }
        log.info("searchJpql={}",searchJpql);
        List<Event> eventList = em.createQuery(searchJpql, Event.class).getResultList();
        for (Event event : eventList) {
            Long event_id = event.getEvent_id();
            String search_cinema_name = event.getCinema().getName();
            String title = event.getTitle();
            String thumbnail_url = event.getThumbnail_url();
            Date start_date = event.getStart_date();
            Date end_date = event.getEnd_date();

            Boolean search_is_end = Boolean.FALSE;
            Date date_now = java.sql.Date.valueOf(now);
            if (end_date.before(date_now)) {
                search_is_end = Boolean.TRUE;
            }

            Boolean is_like=likeBasketsService.isLikeEvent(user_id, event_id);

            searchList.add(new EventsSearchResponseDto(search_cinema_name, event_id, thumbnail_url, title, start_date, end_date, is_like, search_is_end));
        }
        return searchList;
    }

    @Transactional(rollbackFor = Exception.class)
    public EventsDetailResponseDto detail(EventsDetailRequestDto requestDto) throws ParseException {

        Long event_id = requestDto.getEvent_id();
        Long user_id = requestDto.getUser_id();

        Event event = eventRepository.findById(event_id).orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 없습니다. event_id = "+ event_id));
        String cinema_name = event.getCinema().getName();
        String title = event.getTitle();
        String detail_image_url = event.getDetail_image_url();
        String link_url = event.getLink_url();
        Date start_date = event.getStart_date();
        Date end_date = event.getEnd_date();
        Long like_count = event.getLike_count();
        Boolean is_like = likeBasketsService.isLikeEvent(user_id, event_id);

        EventsDetailResponseDto result =new EventsDetailResponseDto(event_id, cinema_name, title, detail_image_url, link_url, start_date, end_date, like_count, is_like);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultResponseDto like(EventsLikeRequestDto requestDto) throws ParseException {

        Long event_id = requestDto.getEvent_id();
        Long user_id = requestDto.getUser_id();

        Boolean is_like = likeBasketsService.isLikeEvent(user_id, event_id);
        Event event = eventRepository.findById(event_id).orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 없습니다. event_id = "+ event_id));
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. user_id = "+ user_id));

        Boolean result = false;
        if (Objects.equals(is_like,false)) {
            Like_Basket saveEntity=Like_Basket.builder().user(user).event(event).build();
            likeBasketRepository.save(saveEntity);
            result = true;
        }
        if (Objects.equals(is_like, true)) {
            Long like_basket_id = likeBasketsService.selectLikeEvent(user_id,event_id);
            result = likeBasketsService.deleteLike(like_basket_id,user_id);
        }

        ResultResponseDto resultResponseDto = new ResultResponseDto();
        resultResponseDto.setResult(result);

        return resultResponseDto;

    }
}
