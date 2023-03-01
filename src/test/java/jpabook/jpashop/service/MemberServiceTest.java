package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //Junit 실행할 때, 스프링과 엮어서 실행하겠다는 의미
@SpringBootTest //Springboot를 띄운 상태에서 테스트하기 위해
@Transactional//테스트 클레스에서는 롤백 사용하기 위해 (DB 쿼리는 트랜잭션 커밋 때 em flushing을 통해 반영되기때문)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given - ~가 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when - ~하면
        Long saveId = memberService.join(member);

        //then - ~된다함
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)//try exception 없이 익셉션 터치기를 통해 테스트 가
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when

        memberService.join(member1);
        memberService.join(member2);

        //then

        fail("예외가 발생해야 한다.");
    }
}