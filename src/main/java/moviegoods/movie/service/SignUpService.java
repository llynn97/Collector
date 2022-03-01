package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.signup.SignUpDuplicateCheckRequestDto;
import moviegoods.movie.domain.dto.signup.SignUpRequestDto;
import moviegoods.movie.domain.entity.User.Authority;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static String basicUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlx2rvqRVwn6S5uXPkHl856CcYvV2z8bUMyw&usqp=CAU";
    private static Long basicReliability = 0L;
    private static Byte basicStatus = 1;

    public ResultResponseDto saveUser(SignUpRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();

        String encodedPassword = passwordEncoder.encode(password);
        User saveEntity = User.builder().authority(Authority.일반).email(email).status(basicStatus).reliability(basicReliability).nickname(nickname).password(encodedPassword).profile_url(basicUrl).build();

        ResultResponseDto resultResponseDto = new ResultResponseDto();
        userRepository.save(saveEntity);
        resultResponseDto.setResult(true);

        return resultResponseDto;
    }
    public ResultResponseDto duplicateCheck(SignUpDuplicateCheckRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        User user = new User();
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }
        if (nickname != null) {
            user = userRepository.findByNickname(nickname).orElse(null);
        }

        ResultResponseDto resultResponseDto = new ResultResponseDto();
        resultResponseDto.setResult(true);

        if (user == null) {
            resultResponseDto.setResult(false);
        }


        return resultResponseDto;
    }

}