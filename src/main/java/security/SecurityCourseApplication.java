package security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
public class SecurityCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCourseApplication.class, args);
    }
    @Bean
    public UserDetailsService userDetailsService(DataSource source){
        return new JdbcUserDetailsService(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizedHttpRequests -> authorizedHttpRequests.requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated())

                //example adding entry point
//                .exceptionHandling((ExceptionHandlingConfigurer<HttpSecurity> exceptionHandlingCustomizer) -> {
//            exceptionHandlingCustomizer.authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
//                response.sendRedirect("/public/sign-in.html");
//
//            });
//        })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll).
                build();


//    old deprecated
//				.httpBasic().and()
//				.authorizeHttpRequests()
//				.anyRequest().authenticated()
//				.build();

    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().GET("/api/v4/greeting", request -> {
            UserDetails userDetails = request.principal().map(Authentication.class::cast).map(Authentication::getPrincipal).map(UserDetails.class::cast).orElseThrow();
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("greeting", "hello, %S".formatted(userDetails.getUsername())));
        }).build();
    }
}
