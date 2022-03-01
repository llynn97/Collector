package moviegoods.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signup.SignUpDuplicateCheckRequestDto;
import moviegoods.movie.domain.dto.signup.SignUpRequestDto;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.SignUpService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public ResultResponseDto signup(@RequestBody SignUpRequestDto requestDto){
        ResultResponseDto resultResponseDto = signUpService.saveUser(requestDto);
        return resultResponseDto;
    }

    @PostMapping("/duplicate-check")
    public ResultResponseDto duplicateCheck(@RequestBody SignUpDuplicateCheckRequestDto requestDto){
        ResultResponseDto resultResponseDto = signUpService.duplicateCheck(requestDto);
        return resultResponseDto;
    }

}


