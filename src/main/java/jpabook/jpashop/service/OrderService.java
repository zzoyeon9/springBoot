package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {//이곳처럼 서비스 계층이 단순히 엔티티에 필요한 요청들을 위임하는 역할을 하고
                            //엔티티가 비즈니스 로직을 가지고 객체지향의 특성을 적극 활용하는 것을
                            // 도메인 모델 패턴이라고 함(반대는 트랜잭션 스크립트 패턴)

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cencelOrder(Long orderId) {//JPA덕에 이렇게 엔티티안의 데이터만 바꾸면 JPA가
                                            // 알아서 더티 체킹(변경 내역 감지)하여 변경 내역들을 찾아
        //엔티티 조회                        // 데이터베이스에서 변경된 각 엔티티의 데이터들을 찾아 모두 업데이트
       Order order = orderRepository.findOne(orderId);

        //주문 취소
       order.cancel();

    }

    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
