package js.toy.member.application.dto;

import js.toy.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberCreateRequest {
    private String email;
    private String name;
    private MemberRole role;
}
