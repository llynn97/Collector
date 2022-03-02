package moviegoods.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.dto.signin.SignInResponseDto;
import moviegoods.movie.service.SignInService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/signin")
    public SignInResponseDto login(@RequestBody SignInRequestDto requestDto, HttpServletRequest httpServletRequest, Model model) {

        return signInService.login(requestDto, httpServletRequest, model);
    }

    @ResponseBody
    @GetMapping("app/users/kakao")
    public void  kakaoCallback(@RequestParam String code){

        System.out.println(code);

    }

}