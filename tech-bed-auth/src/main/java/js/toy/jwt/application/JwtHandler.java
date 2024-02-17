package js.toy.jwt.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Component
public class JwtHandler {

    @Value("${jwt.expire-milli-seconds}")
    private long expireMilliSeconds;

    @Value("${jwt.secret}")
    private String secret;

    public String createAccessToken(String email, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireMilliSeconds);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireMilliSeconds);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(expiryDate)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String headerValue = request.getHeader("Authorization");
        if (headerValue == null) {
            return null;
        }

        return headerValue.substring(headerValue.indexOf("Bearer ") + 7);
    }

    public boolean validate(String token) {
        if (token == null) {
            return false;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (RuntimeException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String accessToken) {
        String email = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();

        // roles는 항상 문자열로 입력되기 때문에 형변환해도 문제없다.
        @SuppressWarnings(value = {"unchecked"})
        List<String> roles = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(accessToken)
                .getBody()
                .get("roles", List.class);

        return new UsernamePasswordAuthenticationToken(
                email,
                "",
                createAuthorityList(roles)
        );
    }

}
