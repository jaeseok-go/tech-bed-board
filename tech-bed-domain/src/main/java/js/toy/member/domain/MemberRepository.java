package js.toy.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmail(String email);

    Member save(Member member);
}
