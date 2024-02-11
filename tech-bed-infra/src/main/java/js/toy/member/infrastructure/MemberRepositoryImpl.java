package js.toy.member.infrastructure;

import js.toy.member.domain.Member;
import js.toy.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }
}
