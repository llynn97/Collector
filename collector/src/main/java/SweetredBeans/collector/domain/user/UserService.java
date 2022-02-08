package SweetredBeans.collector.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static String basicUrl = "기본 프로필 url";
    private static Long basicReliability = 0L;
    private static Byte basicStatus = 0;

    public void saveUser(User user) {
        user.setProfile_url(basicUrl);
        user.setAuthority(Authority.BASIC);
        user.setReliability(basicReliability);
        user.setStatus(basicStatus);

        userRepository.save(user);
    }

}
