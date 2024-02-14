package js.toy.oauth.application;

import js.toy.member.application.MemberService;
import js.toy.member.application.dto.MemberCreateRequest;
import js.toy.member.application.dto.MemberModifyRequest;
import js.toy.member.application.exception.MemberNotFoundException;
import js.toy.member.domain.MemberRole;
import js.toy.oauth.domain.OAuth2Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 정보 가져오기
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 회원 정보에 접근하기 위한 데이터 추출
        String email = String.valueOf(attributes.get("email"));
        String name = String.valueOf(attributes.get("name"));
        MemberRole role = MemberRole.USER;

        saveMember(email, name, role);

        return new OAuth2Member(
                registrationId,
                attributes,
                List.of(new SimpleGrantedAuthority(role.getRoleKey())),
                email
        );
    }

    // 회원정보를 최신정보로 수정, 회원정보가 없으면 회원가입
    private void saveMember(String email, String name, MemberRole role) {
        try {
            memberService.modify(new MemberModifyRequest(email, name, role));
        } catch (MemberNotFoundException e) {
            memberService.create(new MemberCreateRequest(email, name, role));
        }
    }
}
