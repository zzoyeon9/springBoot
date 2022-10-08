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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member  = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);
            em.flush();
            em.clear();

           /* TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            Query query2 = em.createQuery("select m.username, m.age from Member m");

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
*/
           String query= "select m from Member m left join Team t on m.username = t.name";
           em.createQuery(query, Member.class)
                   .setFirstResult(0)
                   .setMaxResults(10)
                   .getResultList();

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
