package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.Manager.ManagerResponseDto;
import moviegoods.movie.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/report")
    public ResponseEntity<List<ManagerResponseDto>> show() {
        List<ManagerResponseDto> reportsList = managerService.show();

        ResponseEntity<List<ManagerResponseDto>> result = new ResponseEntity<>(reportsList, HttpStatus.OK);
        return result;
    }
}
