package moviegoods.movie.service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.configure.ApiKeyConfig;
import moviegoods.movie.configure.GoogleConfigUtils;
import moviegoods.movie.domain.dto.signin.SignInRequestDto;
import moviegoods.movie.domain.dto.signin.SignInResponseDto;
import moviegoods.movie.domain.dto.signup.SignUpRequestDto;
import moviegoods.movie.domain.entity.User.Method;
import moviegoods.movie.domain.entity.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import moviegoods.movie.configure.SessionConfig.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class SignInService {

    private final PasswordEncoder passwordEncoder;
    private final SignUpService signUpService;
    private final EntityManager em;
    private final HttpServletResponse response;
    private final GoogleConfigUtils googleConfigUtils;

    public SignInResponseDto login(SignInRequestDto requestDto, HttpServletRequest request) {
        SignInResponseDto signInResponseDto;
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        String method = requestDto.getMethod();

        String searchJpql = "select u from user u where u.email = '" + email + "' and u.method = '" + method + "'";
        User user;
        try{
            user = em.createQuery(searchJpql, User.class).getSingleResult();

        }catch (NoResultException e){
            signInResponseDto = new SignInResponseDto(null,null,false);
            return signInResponseDto;
        }

        String existPassword = user.getPassword();

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

    public String getKaKaoAccessToken(String code){

        String access_Token="";
        String refresh_Token ="";

        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=e64599af67aac20483ad02a14a8c5058"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:3000/signin/oauth2/code/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public SignInRequestDto getUserInfo(String access_Token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        SignInRequestDto signInRequestDto = new SignInRequestDto();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String password = email + "kakao";

            SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
            signUpRequestDto.setNickname(nickname);
            signUpRequestDto.setEmail(email);
            signUpRequestDto.setPassword(password);
            String searchJpql = "select u from user u where u.email = '" + email + "' and u.method = '카카오'";
            List<User> user = em.createQuery(searchJpql, User.class).getResultList();

            if (user.size() == 0) {
                signUpService.saveUser(signUpRequestDto, Method.카카오);
            }

            signInRequestDto.setEmail(email);
            signInRequestDto.setPassword(password);
            signInRequestDto.setMethod("카카오");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return signInRequestDto;
    }

    public void googleRequest() {
        String baseUrl = "https://accounts.google.com/o/oauth2/v2/auth";

        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", "435089655733-6v1fo661d0dda2ue3ql61420dtquril1.apps.googleusercontent.com");
        params.put("redirect_uri", "http://localhost:8080/signin/auth/google/callback");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirectUrl = baseUrl + "?" + parameterString;

        try {
            response.sendRedirect(redirectUrl);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String googleRequestAccessToken(String code) {
        String access_Token = "";
        String refresh_Token ="";

        String reqURL = "https://oauth2.googleapis.com/token";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("code=" + code);
            sb.append("&client_id=435089655733-6v1fo661d0dda2ue3ql61420dtquril1.apps.googleusercontent.com");
            sb.append("&client_secret=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");
            sb.append("&redirect_uri=http://localhost:8080/signin/auth/google/callback");
            sb.append("grant_type=authorization_code");
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

//            Map<String, Object> params = new HashMap<>();
//            params.put("code", code);
//            params.put("client_id", "435089655733-6v1fo661d0dda2ue3ql61420dtquril1.apps.googleusercontent.com");
//            params.put("client_secret", "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");
//            params.put("redirect_uri", "http://localhost:8080/signin/auth/google/callback");
//            params.put("grant_type", "authorization_code");
//
//            String parameterString = params.entrySet().stream()
//                    .map(x -> x.getKey() + "=" + x.getValue())
//                    .collect(Collectors.joining("&"));

//            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
//            bous.write(parameterString.getBytes());
//            bous.flush();
//            bous.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

//            if(conn.getResponseCode() == 200) {
//                return sb.toString();
//            }
//            return "구글 로그인 요청 처리 실패";
            log.info("access_token = {}", access_Token);
            br.close();
            bw.close();
        }catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 구글 로그인 Access Token 요청 URL 입니다 :: " + "https://oauth2.googleapis.com/token");
        }
        //return googleConfigUtils.requestAccessTokenUsingURL(code);
        return access_Token;

    }

}