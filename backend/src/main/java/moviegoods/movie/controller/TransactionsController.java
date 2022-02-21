package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
<<<<<<< HEAD
=======
import moviegoods.movie.domain.dto.events.EventsLikeRequestDto;
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.service.TransactionsService;
import moviegoods.movie.domain.dto.transactions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import moviegoods.movie.configure.SessionConfig.*;

import javax.servlet.http.HttpSession;
<<<<<<< HEAD
=======
import java.text.ParseException;
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping("/write")
    public ResultResponseDto write(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                               User loginUser,
                                   @RequestBody TransactionsSaveRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.write(loginUser,requestDto);
        return resultResponseDto;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionsSearchResponseDto>>  search(@ModelAttribute TransactionsSearchRequestDto requestDto, HttpSession session) {
        List<TransactionsSearchResponseDto> list = transactionsService.search(requestDto, session);
        ResponseEntity<List<TransactionsSearchResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }

    @PostMapping("/change-status")
    public ResultResponseDto changeStatus(@RequestBody TransactionsChangeStatusRequestDto requestDto, HttpSession session) {
        ResultResponseDto resultResponseDto = transactionsService.changeStatus(requestDto);
        return resultResponseDto;
    }

    @DeleteMapping
    public ResultResponseDto delete(@RequestBody TransactionsDeleteRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.delete(requestDto);
        return resultResponseDto;
    }

    @PostMapping("/report")
    public ResultResponseDto report(@RequestBody TransactionsReportRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.report(requestDto);
        return resultResponseDto;
    }
<<<<<<< HEAD
=======

    @PostMapping("/like")
    public ResultResponseDto like(@RequestBody TransactionsLikeRequestDto requestDto) throws ParseException {
        ResultResponseDto resultResponseDto = transactionsService.like(requestDto);
        return resultResponseDto;
    }
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
}
