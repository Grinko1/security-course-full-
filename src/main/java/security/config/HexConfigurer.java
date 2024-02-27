package security.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class HexConfigurer extends AbstractHttpConfigurer<HexConfigurer, HttpSecurity> {
    private AuthenticationEntryPoint authenticationEntryPoint = (request, response, authException) -> {
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Hex");
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    };

    @Override
    public void init(HttpSecurity builder) throws Exception {
       builder.exceptionHandling(c -> c.authenticationEntryPoint(this.authenticationEntryPoint));
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilterBefore(new HexAuthenticationFilter(authenticationManager, this.authenticationEntryPoint), BasicAuthenticationFilter.class);
    }

    public HexConfigurer authenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        return this;

    }
}
