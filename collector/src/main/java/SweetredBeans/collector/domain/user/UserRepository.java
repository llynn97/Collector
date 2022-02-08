package SweetredBeans.collector.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    User findByEmail(String email);
//    private static Map<Long, User> store = new ConcurrentHashMap<>();
//    private static long sequence = 0L;
//    private static String basicUrl = "기본 프로필 url";
//    private static Long basicReliability = 0L;
//    private static Byte basicStatus = 0;


//    public User save(User user) {
//        //user.setId(++sequence);
//        user.setProfile_url(basicUrl);
//        user.setAuthority(Authority.BASIC);
//        user.setReliability(++basicReliability);
//        user.setStatus(basicStatus);
//        log.info("save: user={}", user);
//
//
//        //store.put(user.getId(), user);
//        return user;
//    }

//    public User findById(Long id) {
//        return store.get(id);
//    }
//
//    public Optional<User> findByLoginEmail(String email) {
//        return findAll().stream()
//                .filter(u -> u.getEmail().equals(email))
//                .findFirst();
//    }
//
//    //이미 존재하는 이메일이면(중복) true, 아니면 false;
//    public Boolean isDuplicateEmail(String email) {
//        log.info("isDuplicateEmail");
//        List<User> all = findAll();
//        for (User u : all) {
//            if(u.getEmail().equals(email)) {
//                log.info("dupEmail= {}", email);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    //이미 존재하는 닉네임이면(중복) true, 아니면 false;
//    public Boolean isDuplicateNickname(String nickname) {
//        log.info("isDuplicateNickname");
//        List<User> all = findAll();
//        for (User u : all) {
//            if(u.getNickname().equals(nickname)) {
//                log.info("dupNickname= {}", nickname);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public List<User> findAll() {
//        return new ArrayList<>(store.values());
//    }
//
//    public void clearStore() {
//        store.clear();
//    }
}