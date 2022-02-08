package SweetredBeans.collector.domain.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String email;

    @NotBlank
    @Column(length = 30)
    private String nickname;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String password;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String profile_url;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @NotNull
    private Long reliability = 0L;

    @NotNull
    private Byte status = 0;
}