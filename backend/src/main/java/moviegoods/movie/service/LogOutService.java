package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.configure.SessionConfig;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.logout.LogOutRequestDto;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogOutService {

    public ResultResponseDto logout(LogOutRequestDto requestDto, HttpSession session) {
        Long user_id = requestDto.getUser_id();

        ResultResponseDto resultResponseDto = new ResultResponseDto();

        boolean result = false;
        if (session != null) {
            session.setAttribute(SessionConfig.SessionConst.LOGIN_MEMBER, null);
            log.info("sessionId= {}", session.getId());
            session.invalidate();
            resultResponseDto.setResult(true);

        }
        else {
            resultResponseDto.setResult(false);
        }

        return resultResponseDto;
    }
}

