package moviegoods.movie.domain.dto.signin;

import lombok.Getter;
import moviegoods.movie.domain.entity.User.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getProfile_url();
    }
}
