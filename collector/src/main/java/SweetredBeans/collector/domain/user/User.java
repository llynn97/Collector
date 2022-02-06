package SweetredBeans.collector.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String profile_url;
    private Authority authority;
    private Integer reliability;
    private Boolean status;

    public User() {
    }

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", authority=" + authority +
                ", reliability=" + reliability +
                ", status=" + status +
                '}';
    }
}
