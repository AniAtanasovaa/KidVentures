package defence.app.configuration;
import defence.app.model.enums.RoleEnum;
import defence.app.repository.UserRepository;
import defence.app.service.impl.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                // Дефинирам, кои urls са видими и точно от кои потребители
                authorizeRequests -> authorizeRequests
                        // Всички статични ресурси, които се намират в js, изображения, css са достъпни за всеки
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // Allow anyone to see the home page, the registration page and the login form
                        .requestMatchers("/", "/login", "/register", "/login-error", "/about").permitAll()
                        .requestMatchers("/places/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/places/**").permitAll()
                        .requestMatchers("/error").permitAll()
                       .requestMatchers("/account").hasRole(RoleEnum.ADMIN.name())
                       .requestMatchers("/change").hasRole(RoleEnum.ADMIN.name())
                        // all other requests are authenticated.
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            // redirect here when we access something which is not allowed.
                            // also this is the page where we perform login.
                            .loginPage("/login")
                            // The names of the input fields (in our case in auth-login.html)
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/",true)
                            .failureForwardUrl("/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            // the URL where we should POST something in order to perform the logout
                            .logoutUrl("/logout")
                            // where to go when logged out?
                            .logoutSuccessUrl("/")
                            // invalidate the HTTP session
                            .invalidateHttpSession(true);
                }
        )

                .build();
    }

    @Bean //@Bean анотацията се използва за маркиране на метод, който създава и връща обект, който ще бъде
    // управляван от Spring контейнера. В случая, методът създава UserDetailsService обект,
    // който ще бъде използван за аутентикация на потребителите.
    public UserDetailsService userDetailsService(UserRepository userRepository) {

        //Тази услуга превежда потребителите и ролите към представяне, което Spring сигурността разбира.
        //Методът връща обект от тип ApplicationUserDetailsService. Този обект ще бъде инжектиран от Spring
        // контейнера в приложението, където е необходимо извличане на информация за потребителите
        // при аутентикация.

        return new ApplicationUserDetailsService(userRepository);//Този клас представлява конкретна имплементация
        // на UserDetailsService
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }


}
//HttpSecurity е обект в Spring Security, който представлява основната конфигурация за сигурност на приложението.
// //HttpSecurity позволява детайлно конфигуриране на правилата за сигурност във приложението,
// като контролира как се осъществява аутентикацията, авторизацията и други аспекти на сигурността.
// Този обект предоставя методи за конфигуриране на различни аспекти на защитата на приложението, като настройка
// на разрешенията за достъп до определени URL адреси, конфигуриране на формата за вход, обработка на изход и други.
///Методът filterChain(HttpSecurity httpSecurity) връща SecurityFilterChain, който представлява конфигурацията
// за филтриране на HTTP заявките.
// Чрез метода authorizeHttpRequests се дефинират разрешения за достъп до различни URL адреси.
// След това се конфигурира формата за вход и изход с .formLogin и .logout секциите.
// Накрая, чрез .build() се създава и връща SecurityFilterChain.
