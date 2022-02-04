package SweetredBeans.collector.web.user;

import SweetredBeans.collector.domain.user.User;
import SweetredBeans.collector.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserRepository userRepository = new UserRepository();

    @AfterEach
    void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void Save() {
        User user = new User();
        user.setEmail("aaa");
        user.setNickname("bbb");
        user.setPassword("ccc");

        User saveUser = userRepository.save(user);

        User findUser = userRepository.findById(user.getId());
        assertThat(findUser).isEqualTo(saveUser);
    }

    @Test
    void Duplicate() {
        User user = new User();
        user.setEmail("aaa");
        user.setNickname("bbb");
        user.setPassword("ccc");

        User user2 = new User();
        user2.setEmail("aaa");
        user2.setNickname("bbc");
        user2.setPassword("ccc");

        User saveUser = userRepository.save(user);
        User saveUser2 = userRepository.save(user2);

    }

}