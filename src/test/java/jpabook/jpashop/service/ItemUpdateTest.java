package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;


    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1);

        //TX
        book.setName("asdasd");

        //변경감지 == Dirty Checking <- 덕분에 DB에 쿼리문을 날려 업데이트할 필요 없이 편리하게 엔티티 데이터 변경이 가능
        //TX commit

        //준영속 엔티티란? 영속성 컨테스트 더이상 관리하지 않는 엔티티(쉽게 말해 한번 DB에 갔다온 데이터)    }
        //준영속 엔티티의 가장 큰 문제는 JPA가 관리하지 않기 때문에 변경감지를 하지 못함
        //그래서 준영속 엔티티를 수정하는 2가지 방법이 있는데
        //1. 변경 감지(Dirty-Checking) 기능 사용 2. Merge 사용 이 있다.
        //변경 감지 : 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
        //병합 : 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용
        //그냥 쉽게 생각하면 엔티티 변경할 때는 무조건 변경 감지를 사용하자
    }
}
