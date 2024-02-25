package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class SecurityCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityCourseApplication.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic(httpBasic -> {})
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.build();




		//old deprecated
//				.httpBasic().and()
//				.authorizeHttpRequests()
//				.anyRequest().authenticated()
//				.build();
	}
}
