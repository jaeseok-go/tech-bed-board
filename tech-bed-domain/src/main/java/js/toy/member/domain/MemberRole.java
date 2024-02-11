package js.toy.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String roleKey;
}
