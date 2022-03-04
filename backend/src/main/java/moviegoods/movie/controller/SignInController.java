package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.dto.signin.SignInResponseDto;
import moviegoods.movie.domain.dto.signup.SignUpDuplicateCheckRequestDto;
import moviegoods.movie.domain.dto.signup.SignUpRequestDto;
import moviegoods.movie.domain.entity.Event.Event;
import moviegoods.movie.domain.entity.User.Method;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import moviegoods.movie.service.SignInService;
import moviegoods.movie.service.SignUpService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {

    private final SignInService signInService;

    @PostMapping
    public SignInResponseDto login(@RequestBody SignInRequestDto requestDto, HttpServletRequest httpServletRequest) {

        return signInService.login(requestDto, httpServletRequest);
    }

    @ResponseBody
    @GetMapping("/oauth2/code/kakao")
    public SignInRequestDto  kakaoCallback(@RequestParam String code, HttpServletRequest httpServletRequest){

        String access_Token = signInService.getKaKaoAccessToken(code);
        SignInRequestDto userInfo = signInService.getUserInfo(access_Token);

        return userInfo;

    }

    @GetMapping("/auth/google")
    public void googleLogin() {
        signInService.googleRequest();
    }

    @GetMapping("/auth/google/callback")
    public String googleCallback(@RequestParam String code) {
        return signInService.googleRequestAccessToken(code);
    }

}