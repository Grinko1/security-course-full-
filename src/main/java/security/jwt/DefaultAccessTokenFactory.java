package security.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class DefaultAccessTokenFactory implements Function<Token, Token> {
    private Duration duration = Duration.ofMinutes(5);

    @Override
    public Token apply(Token token) {
        return new Token(token.id(), token.subject(),
                token.authorities().stream()
                        .filter(authority -> authority.startsWith("GRANT_"))
                        .map(authority -> authority.replace("GRANT_", ""))
                        .toList(),
                Instant.now(),
                Instant.now().plus(this.duration)
        );
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
