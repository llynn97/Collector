package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.argumentresolver.Login;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.events.EventsLikeRequestDto;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.service.TransactionsService;
import moviegoods.movie.domain.dto.transactions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import moviegoods.movie.configure.SessionConfig.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping("/write")
    public ResultResponseDto write(@Login User loginUser,
                                   @RequestBody TransactionsSaveRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.write(loginUser,requestDto);
        return resultResponseDto;
    }
    //HttpServletRequest request,@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
    @PostMapping("/search")
    public ResponseEntity<List<TransactionsSearchResponseDto>>  search(@Login User loginUser, @ModelAttribute TransactionsSearchRequestDto requestDto) {

        List<TransactionsSearchResponseDto> list = transactionsService.search(loginUser, requestDto);
        ResponseEntity<List<TransactionsSearchResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }

    @PostMapping("/change-status")
    public ResultResponseDto changeStatus(@Login User loginUser, @RequestBody TransactionsChangeStatusRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.changeStatus(loginUser, requestDto);
        return resultResponseDto;
    }

    @DeleteMapping
    public ResultResponseDto delete(@Login User loginUser, @RequestBody TransactionsDeleteRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.delete(loginUser, requestDto);
        return resultResponseDto;
    }

    @PostMapping("/report")
    public ResultResponseDto report(@Login User loginUser, @RequestBody TransactionsReportRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.report(loginUser, requestDto);
        return resultResponseDto;
    }

    @PostMapping("/like")
    public ResultResponseDto like(@Login User loginUser, @RequestBody TransactionsLikeRequestDto requestDto) throws ParseException {
        ResultResponseDto resultResponseDto = transactionsService.like(loginUser, requestDto);
        return resultResponseDto;
    }
}
