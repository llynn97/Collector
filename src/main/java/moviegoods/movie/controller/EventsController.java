package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.configure.SessionConfig;
import moviegoods.movie.domain.argumentresolver.Login;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.events.*;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.service.EventsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventsController {

    private final EventsService eventsService;

    @GetMapping("/search")
    public ResponseEntity<List<EventsSearchResponseDto>> search(@Login User loginUser, @ModelAttribute EventsSearchRequestDto requestDto) throws ParseException {
        List<EventsSearchResponseDto> list = eventsService.search(loginUser, requestDto);
        ResponseEntity<List<EventsSearchResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }

    @GetMapping("/detail")
    public ResponseEntity<EventsDetailResponseDto> detail(@Login User loginUser, @ModelAttribute EventsDetailRequestDto requestDto) throws ParseException {
        EventsDetailResponseDto detail_result = eventsService.detail(loginUser, requestDto);
        ResponseEntity<EventsDetailResponseDto> result = new ResponseEntity<>(detail_result, HttpStatus.OK);
        return result;
    }

    @PostMapping("/like")
    public ResultResponseDto like(@SessionAttribute(name = SessionConfig.SessionConst.LOGIN_MEMBER, required = false)
                                              User loginUser, @RequestBody EventsLikeRequestDto requestDto) throws ParseException {
        ResultResponseDto resultResponseDto = eventsService.like(loginUser, requestDto);
        return resultResponseDto;
    }

}
