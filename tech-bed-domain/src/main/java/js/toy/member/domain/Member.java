package js.toy.member.domain;

import jakarta.persistence.*;
import js.toy.common.BaseEntity;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<MemberRole> roles;

    @Builder
    public Member(String email, String name, List<MemberRole> roles) {
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
}
