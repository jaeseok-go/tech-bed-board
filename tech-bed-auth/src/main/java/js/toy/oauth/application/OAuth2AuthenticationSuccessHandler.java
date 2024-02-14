package js.toy.oauth.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import js.toy.jwt.application.JwtHandler;
import js.toy.oauth.domain.OAuth2Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtHandler jwtHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2Member oAuth2Member = (OAuth2Member) authentication.getPrincipal();
        String email = oAuth2Member.getEmail();
        List<String> authorities = oAuth2Member.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = jwtHandler.createAccessToken(email, authorities);
        String refreshToken = jwtHandler.createRefreshToken();

        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8000)
                .path("/oauth")
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .build()
                .toUri()
                .toString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}
