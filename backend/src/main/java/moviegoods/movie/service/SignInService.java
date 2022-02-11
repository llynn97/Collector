package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.configure.SessionConfig;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.dto.signin.SignInResponseDto;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;
import moviegoods.movie.configure.SessionConfig.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInResponseDto login(SignInRequestDto requestDto, HttpServletRequest request) {
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();

        User user = userRepository.findByEmail(email).orElse(null);
        String existPassword = user.getPassword();
        SignInResponseDto signInResponseDto;

        if(passwordEncoder.matches(password, existPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, user);
            signInResponseDto = new SignInResponseDto(user.getNickname(), user.getProfile_url(), true);
        }
        else {
            signInResponseDto = new SignInResponseDto(null,null,false);
        }

        return signInResponseDto;

    }
}