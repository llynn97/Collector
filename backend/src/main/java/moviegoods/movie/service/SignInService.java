package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignInService {

    public abstract class SessionConst {
        public static final String LOGIN_MEMBER = "loginMember";
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResultResponseDto login(SignInRequestDto requestDto, HttpSession session) {
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();

        User loginUser = userRepository.findByEmail(email).orElse(null);
        String existPassword = loginUser.getPassword();
        ResultResponseDto resultResponseDto = new ResultResponseDto();

        if(passwordEncoder.matches(password, existPassword)) {
            resultResponseDto.setResult(true);
            session.setAttribute(SessionConst.LOGIN_MEMBER,loginUser);
        }
        else {
            resultResponseDto.setResult(false);

        }

        return resultResponseDto;

    }
}