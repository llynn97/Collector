package moviegoods.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.logout.LogOutRequestDto;
import moviegoods.movie.service.LogOutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogOutController {

    private final LogOutService logOutService;

    @PostMapping("/logout")
    public ResultResponseDto logout(@RequestBody LogOutRequestDto requestDto, HttpSession session) throws IOException {
        ResultResponseDto resultResponseDto = logOutService.logout(requestDto, session);
        return resultResponseDto;
    }
}
