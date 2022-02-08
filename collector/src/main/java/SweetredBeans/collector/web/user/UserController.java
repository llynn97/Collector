package SweetredBeans.collector.web.user;

import SweetredBeans.collector.domain.user.SHA256;
import SweetredBeans.collector.domain.user.User;
import SweetredBeans.collector.domain.user.UserRepository;
import SweetredBeans.collector.domain.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //h2연결
    @PostMapping
    public void signup(@RequestBody User user, HttpServletResponse response) throws IOException {
        Boolean result = false;

        log.info("user.email= {}", user.getEmail());
        log.info("user.nickname= {}", user.getNickname());
        //암호확인
        log.info("user.password= {}", user.getPassword());
        //비밀번호 암호화
        String encryptPasssword = SHA256.encrypt(user.getPassword());
        user.setPassword(encryptPasssword);
        log.info("encrypt password= {}", encryptPasssword);

        userService.saveUser(user);
        result = true;

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("result", Boolean.toString(result));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(userMap));
    }

    //true면 중복, false면 중복x
    @PostMapping("/duplicate-check")
    public void emailDuplicateCheck(@RequestBody User user, HttpServletResponse response) throws IOException {
        Boolean result = false;
        String email = user.getEmail();
        String nickname = user.getNickname();

        //forTest();

        if(email == null) {
            if(userRepository.existsByNickname(nickname)) {
                result = true;
            }
            else {
                result = false;
            }
        }
        if(nickname == null) {
            if(userRepository.existsByEmail(email)) {
                result = true;
            }
            else {
                result = false;
            }
        }

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("result", Boolean.toString(result));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(userMap));

    }

}


