package moviegoods.movie.configure;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import moviegoods.movie.service.SignInOauth2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
=======
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
<<<<<<< HEAD
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SignInOauth2Service signInOauth2Service;

=======
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable()
<<<<<<< HEAD
                .headers().frameOptions().disable()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(signInOauth2Service);
=======
                .headers().frameOptions().disable();
>>>>>>> 57d200fd9a1e6ea7553b2cee71f8b89f46055647
    }
}