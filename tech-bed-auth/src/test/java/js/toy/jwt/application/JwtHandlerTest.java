package js.toy.jwt.application;

import js.toy.member.domain.MemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JwtHandlerTest {

    private JwtHandler jwtHandler = new JwtHandler();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtHandler, "expireMilliSeconds", 600000);
        ReflectionTestUtils.setField(jwtHandler,"secret", "1CE6EB6421E1BD88C4E144CCE1491");
    }

    @Test
    void access_token을_생성한다() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of(MemberRole.USER.getFullKey());

        // when
        String accessToken = jwtHandler.createAccessToken(email, roles);

        // then
        Assertions.assertTrue(accessToken.contains("."));
    }

    @Test
    void 생성한_access_token은_유효하다() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of(MemberRole.USER.getFullKey());

        // when
        String accessToken = jwtHandler.createAccessToken(email, roles);

        // then
        Assertions.assertTrue(jwtHandler.validate(accessToken));
    }

    @Test
    void access_token에서_유효한_Authentication_인스턴스를_얻는다() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of(MemberRole.USER.getFullKey());
        String accessToken = jwtHandler.createAccessToken(email, roles);

        // when
        Authentication authentication = jwtHandler.getAuthentication(accessToken);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // then
        Assertions.assertInstanceOf(Authentication.class, authentication);
        Assertions.assertTrue(!authorities.isEmpty());
        Assertions.assertTrue(authorities.stream().allMatch(a -> roles.contains(a.getAuthority())));
    }
}