package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.Manager.ManagerRequestDto;
import moviegoods.movie.domain.dto.Manager.ManagerResponseDto;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/report")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<List<ManagerResponseDto>> show() {
        List<ManagerResponseDto> reportsList = managerService.show();

        ResponseEntity<List<ManagerResponseDto>> result = new ResponseEntity<>(reportsList, HttpStatus.OK);
        return result;
    }

    @PatchMapping
    public ResultResponseDto approve(@RequestBody ManagerRequestDto requestDto) {
        ResultResponseDto result = managerService.approve(requestDto.getUser_id());
        return result;
    }
}
