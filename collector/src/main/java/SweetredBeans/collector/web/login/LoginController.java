package SweetredBeans.collector.web.login;

import SweetredBeans.collector.domain.login.LoginService;
import SweetredBeans.collector.domain.entity.User;
import SweetredBeans.collector.domain.user.UserRepository;
import SweetredBeans.collector.web.SessionConst;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final UserRepository userRepository;

    //h2연결
    //로그인 실패시 false, 성공시 true
    @PostMapping("/signin")
    public void login(
            @RequestBody User user, HttpSession session, HttpServletResponse response) throws IOException {
        Boolean result = false;

        User loginUser = loginService.login(user.getEmail(), user.getPassword());

        HashMap<String, String> userMap = new HashMap<>();
        if(loginUser == null) {
            result = false;
            userMap.put("result", Boolean.toString(result));
        }
        else {
            result = true;
            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
            log.info("sessionId= {}", session.getId());
            log.info("isNew={}", session.isNew());

            userMap.put("result", Boolean.toString(result));
            userMap.put("nickname", loginUser.getNickname());
            userMap.put("image_url", loginUser.getProfile_url());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(userMap));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody User user, HttpSession session, HttpServletResponse response) throws IOException {
        boolean result = false;
        if(session != null) {
            session.setAttribute(SessionConst.LOGIN_MEMBER, null);
            log.info("sessionId= {}", session.getId());
            session.invalidate();
            result = true;
        }

        HashMap<String, String> logoutUser = new HashMap<>();
        logoutUser.put("result", Boolean.toString(result));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(logoutUser));
    }
}