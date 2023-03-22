package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component

public class OrderServiceImpl implements OrderService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //@Autowired
    private final MemberRepository memberRepository;

    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //@Autowired
    private final DiscountPolicy discountPolicy;

    /*@Autowired(required = false)
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }*/

    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
