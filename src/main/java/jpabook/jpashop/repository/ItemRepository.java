package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em; //spring data jpa가 persistContext대신에 Autowired로 되게끔 해줌

    public void save(Item item) {//item은 DB에 저장하기 전까진 id값이 존재X
        if (item.getId() == null)//id값이 없다면 완전히 새로 생성한 객체라는 의
            em.persist(item);
        else                //id값이 있다는 건 이미 DB에 있다는 의미이니 업데이트한다고 이해하자
            em.merge(item); //병합 방식. 단, 주의할 점이 있다.
                            // 하나라도 파라미터에 엔티티 요소가 빠져있으면
                            // Null이 들어가버리는 큰 위험이 있으니 지양하자
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i ", Item.class)
                .getResultList();
    }
}
