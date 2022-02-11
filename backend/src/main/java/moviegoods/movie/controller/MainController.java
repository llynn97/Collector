package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.main.MainDailyCommunityRequestDto;
import moviegoods.movie.domain.dto.main.MainDailyCommunityResponseDto;
import moviegoods.movie.domain.dto.main.MainEventLimitResponseDto;
import moviegoods.movie.service.MainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    @GetMapping("/event-limit")
    public ResponseEntity<List<MainEventLimitResponseDto>> eventLimit() throws ParseException {
        List<MainEventLimitResponseDto> list = mainService.eventLimit();
        ResponseEntity<List<MainEventLimitResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }
    @GetMapping("/daily-community")
    public ResponseEntity<List<MainDailyCommunityResponseDto>> dailyCommunity(@ModelAttribute MainDailyCommunityRequestDto requestDto) throws ParseException {
        List<MainDailyCommunityResponseDto> list = mainService.dailyCommunity(requestDto);
        ResponseEntity<List<MainDailyCommunityResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }


}
