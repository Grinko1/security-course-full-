package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

@SpringBootApplication
public class SecurityCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCourseApplication.class, args);
    }



    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().GET("/api/v4/greeting", request -> {
            UserDetails userDetails = request.principal().map(Authentication.class::cast).map(Authentication::getPrincipal).map(UserDetails.class::cast).orElseThrow();
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("greeting", "hello, %S".formatted(userDetails.getUsername())));
        }).build();
    }
}
