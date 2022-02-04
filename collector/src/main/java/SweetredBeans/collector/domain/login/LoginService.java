package SweetredBeans.collector.domain.login;

import SweetredBeans.collector.domain.user.SHA256;
import SweetredBeans.collector.domain.user.User;
import SweetredBeans.collector.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * @return null 로그인 실패
     */
    public User login(String loginEmail, String password) {

        //암호화된 비밀번호
        String encryptPassword = SHA256.encrypt(password);
        log.info("encryptPassword= {}", encryptPassword);

        return userRepository.findByLoginEmail(loginEmail)
                .filter(user -> user.getPassword().equals(encryptPassword))
                .orElse(null);
    }
}
