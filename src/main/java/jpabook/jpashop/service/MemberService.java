package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//조회 전용 메서드에는 해당 옵션 넣으면 성능 최적화
@RequiredArgsConstructor//final이 붙은 것만 인잭
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional//메서드에 지정해놓으면 이게 우선
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }


    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
