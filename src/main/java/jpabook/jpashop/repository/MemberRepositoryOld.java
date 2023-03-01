package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //컴포넌트 스캔 과정에서 자동으로 스프링 빈으로 관리가 됨
@RequiredArgsConstructor//이건 SpringData JPA가 지원해주는 기능
public class MemberRepositoryOld {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);//jpa가 얘를 db에 저장
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)//jpql, 조회 타
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
