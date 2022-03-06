package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.dto.signin.SignInResponseDto;
import moviegoods.movie.service.SignInService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



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
        SignInRequestDto signInRequestDto = signInService.getUserInfo(access_Token);

        return signInRequestDto;
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