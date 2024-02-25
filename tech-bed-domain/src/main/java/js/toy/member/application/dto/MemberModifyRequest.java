package js.toy.member.application.dto;

import js.toy.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberModifyRequest {
    private String email;
    private String name;
    private List<MemberRole> roles;
}
