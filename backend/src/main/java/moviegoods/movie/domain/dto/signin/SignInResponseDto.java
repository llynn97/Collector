package moviegoods.movie.domain.dto.signin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {
    private String nickname;
    private String image_url;
    private Boolean status;
    private Boolean result;
    private String authority;
}
