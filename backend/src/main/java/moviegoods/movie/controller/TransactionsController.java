package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.service.TransactionsService;
import moviegoods.movie.domain.dto.transactions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping("/write")
    public ResultResponseDto write(@RequestBody TransactionsSaveRequestDto requestDto) {
        ResultResponseDto resultResponseDto = transactionsService.write(requestDto);
        return resultResponseDto;
    }
    @GetMapping("/search")
    public ResponseEntity<List<TransactionsSearchResponseDto>>  search(@ModelAttribute TransactionsSearchRequestDto requestDto) {
        List<TransactionsSearchResponseDto> list = transactionsService.search(requestDto);
        ResponseEntity<List<TransactionsSearchResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }
    @PostMapping("/change-status")
    public ResultResponseDto changeStatus(@RequestBody TransactionsChangeStatusRequestDto requestDto) {
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
}
