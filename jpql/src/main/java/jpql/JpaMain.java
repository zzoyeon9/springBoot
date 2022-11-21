package jpql;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1  = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2  = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamB);
            em.persist(member2);

            Member member3  = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

            String query =
                    "select m From Member m join fetch m.team";


            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();
            
            for (Member member : result) {
                System.out.println("member.getUsername() + \" , \" +member.getTeam().getName() = " + member.getUsername() + " , " +member.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
