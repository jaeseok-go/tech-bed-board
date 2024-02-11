package js.toy.oauth.config;

import js.toy.member.domain.MemberRole;
import js.toy.oauth.application.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        return http
                .csrf(CsrfConfigurer::disable)
                .requestCache(cache -> cache
                        .requestCache(requestCache))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .authenticated())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**")
                        .hasRole(MemberRole.USER.getRoleKey()))
                .logout(auth -> auth
                        .logoutSuccessUrl("/"))
                .oauth2Login(auth -> auth
                        .userInfoEndpoint(endPoint -> endPoint
                                .userService(oAuth2UserService)
                ))
                .build();
    }
}
