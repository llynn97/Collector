package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Boolean write(@RequestBody TransactionsSaveRequestDto requestDto) {
        return transactionsService.write(requestDto);
    }
    @GetMapping("/search")
    public ResponseEntity<List<TransactionsSearchResponseDto>>  search(@ModelAttribute TransactionsSearchRequestDto requestDto) {
        List<TransactionsSearchResponseDto> list = transactionsService.search(requestDto);
        ResponseEntity<List<TransactionsSearchResponseDto>> result = new ResponseEntity<>(list, HttpStatus.OK);
        return result;
    }
    @PostMapping("/change-status")
    public Boolean changeStatus(@RequestBody TransactionsChangeStatusRequestDto requestDto) {
        return transactionsService.changeStatus(requestDto);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody TransactionsDeleteRequestDto requestDto) {
        return transactionsService.delete(requestDto);
    }

    @PostMapping("/report")
    public Boolean report(@RequestBody TransactionsReportRequestDto requestDto) {
        return transactionsService.report(requestDto);
    }
}
