package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";

    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {//@RequestParam : HTML form을 submit 할 때,
                                                            // select name=에서 걸어놓은 변수에 데이터가 담겨서
                                                             // @RequestParam 옆의 변수에 저장됨
        orderService.order(memberId, itemId, count);//멀티 상품을 주문하려면 여기랑 화면만 바꾸면 된다고 함
                                                    //여기서 memberId, itemId 찾아서 넘겨도 되지만
                                                    //컨트롤러에게는 식별자만 넘기고 엔티티 사용 및 핵심 비즈니스 로직은
                                                    // 서비스 안에서 해야 영속성이 존재하는 상태에서 사용할 수 있음
        return "redirect:/orders";
        //만약 주문 결과 페이지를 만든다면 저 orderService.order(memberId, itemId, count)를 변수에 받아서
        //return "redirect:/orders" + orderId 이런식으로..
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        //해당과 같은 단순 조회의 경우에는 걍 컨트롤러에서 리파지토리 접근해서 가져와도 됨
        //굳이 서비으세 위임할 필요성이 있는지 알아서 판단
        model.addAttribute("orders", orders);
        //@ModelAttribute 해놓으면 orderSearch가 model에 자동으로 담김
        //model.addAttribute("orderSearch", orderSearch); 가 생략거라고 생각하면 됨

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cencelOrder(orderId);
        return "redirect:/orders";
    }
}
