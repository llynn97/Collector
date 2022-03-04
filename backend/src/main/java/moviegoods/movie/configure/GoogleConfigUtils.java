package moviegoods.movie.configure;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class GoogleConfigUtils {
    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;

    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

//    public String requestAccessTokenUsingURL(String code) {
//        try {
//            URL url = new URL(GOOGLE_SNS_TOKEN_BASE_URL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setDoOutput(true);
//
//            Map<String, Object> params = new HashMap<>();
//            params.put("code", code);
//            params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//            params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
//            params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
//            params.put("grant_type", "authorization_code");
//
//            String parameterString = params.entrySet().stream()
//                    .map(x -> x.getKey() + "=" + x.getValue())
//                    .collect(Collectors.joining("&"));
//
//            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
//            bous.write(parameterString.getBytes());
//            bous.flush();
//            bous.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            StringBuilder sb = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            if(conn.getResponseCode() == 200) {
//                return sb.toString();
//            }
//            return "구글 로그인 요청 처리 실패";
//        }catch (IOException e) {
//            throw new IllegalArgumentException("알 수 없는 구글 로그인 Access Token 요청 URL 입니다 :: " + GOOGLE_SNS_TOKEN_BASE_URL);
//        }
//    }
//
//
//    public String getKaKaoAccessToken(String code){
//
//        String access_Token="";
//        String refresh_Token ="";
//
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        try{
//            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("grant_type=authorization_code");
//            sb.append("&client_id=e64599af67aac20483ad02a14a8c5058"); // TODO REST_API_KEY 입력
//            sb.append("&redirect_uri=http://localhost:3000/signin/oauth2/code/kakao"); // TODO 인가코드 받은 redirect_uri 입력
//            sb.append("&code=" + code);
//            bw.write(sb.toString());
//            bw.flush();
//
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//            br.close();
//            bw.close();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return access_Token;
//    }
//
//    public String googleRequestAccessToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", "435089655733-6v1fo661d0dda2ue3ql61420dtquril1.apps.googleusercontent.com");
//        params.put("client_secret", "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");
//        params.put("redirect_uri", "http://localhost:8080/signin/auth/google/callback");
//        params.put("grant_type", "authorization_code");
//
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity("https://accounts.google.com/o/oauth2/v2/auth", params, String.class);
//
//        log.info("responseEntity.getBody = {}", responseEntity.getBody().toString());
//
//        if(responseEntity.getStatusCode() == HttpStatus.OK) {
//            return responseEntity.getBody();
//        }
//        return "구글 로그인 요청 처리 실패";
//    }
}