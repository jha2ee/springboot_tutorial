package study.hellospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.hellospring.domain.Member;
import study.hellospring.repository.MemberRepository;
import study.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 서비스 코드를 DI 가능하게
    //기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성 -> DI 가능하게 변경함(생성자에 파라미터로 넣어줌)

    public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 확인 X
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
