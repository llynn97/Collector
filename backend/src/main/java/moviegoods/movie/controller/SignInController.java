package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.service.SignInService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/signin")
    public ResultResponseDto login(@RequestBody SignInRequestDto requestDto, HttpSession session) {
        ResultResponseDto result = signInService.login(requestDto, session);

        return result;
    }


}