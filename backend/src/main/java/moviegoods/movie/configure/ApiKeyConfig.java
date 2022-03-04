package moviegoods.movie.configure;

import com.google.api.client.util.Value;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ApiKeyConfig {
    @Value("${KakaoRestApiKey}")
    private String KakaoApiKey;

    public String getKakaoApiKey() {
        return KakaoApiKey;
    }


}
