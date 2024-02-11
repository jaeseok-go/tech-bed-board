package js.toy.member.application;

import js.toy.member.application.dto.MemberCreateRequest;
import js.toy.member.application.dto.MemberModifyRequest;
import js.toy.member.application.exception.MemberAlreadyExistException;
import js.toy.member.application.exception.MemberNotFoundException;
import js.toy.member.domain.Member;
import js.toy.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * @param memberCreateReq
     * @throws MemberAlreadyExistException 이미 회원정보가 존재하는 경우
     */
    public void create(MemberCreateRequest memberCreateReq) {
        String email = memberCreateReq.getEmail();
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberAlreadyExistException();
        }

        memberRepository.save(Member.builder()
                .email(memberCreateReq.getEmail())
                .name(memberCreateReq.getName())
                .role(memberCreateReq.getRole())
                .build());
    }

    /**
     * @param memberModifyReq
     * @throws MemberNotFoundException 수정할 회원정보가 없는 경우
     */
    public void modify(MemberModifyRequest memberModifyReq) {
        String email = memberModifyReq.getEmail();
        if (memberRepository.findByEmail(email).isEmpty()) {
            throw new MemberNotFoundException();
        }

        memberRepository.save(Member.builder()
                .email(memberModifyReq.getEmail())
                .name(memberModifyReq.getName())
                .role(memberModifyReq.getRole())
                .build());
    }
}