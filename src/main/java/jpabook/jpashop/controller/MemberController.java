package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {//컨트롤러는 주로 서비스를 사용

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        //컨트롤러에서 뷰로 넘어갈 때 아래의 데이터를 실어서 뷰로 넘
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {//Member을 안쓰고 굳이 MemberForm을 쓰는 이유는 해당 폼에 필요한 데이터만 정제해서 사용하기 위해.

        if(result.hasErrors())
            return "members/createMemberForm";

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberlist";
    }

    //엔티티를 그대로 쓰기 vs 폼을 쓰기 -> 실무에선 정확히 데이터가 대칭이 안되고 조금씩 화면에서 원하는 데이터가 다름
    //그래서 결국 이렇게 저렇게 변경하고 그러다보면 유지보수가 어려워지기 때문에 각 화면에서 원하는 데이터에 맞게 폼을 생성해서 사용
    //ex. 폼 객체, dto(DataTransObject)를 사용 <-> 엔티티는 핵심 비즈니스 로직에만 사용. 화면 로직을 위해선 왼쪽 방법 쓰자
    //list 메서드도 사실 단순하니까 저렇게 member 엔티티를 사용해서 그대로 뿌린거고 조금 더 복잡해지면 화면에 꼭 필요한 데이터만
    //가지고 출력을 하는 것을 권장. 특히 API를 만들 때는 절 대 엔티티를 외부로 반환하면 안됨.
    //그 이유는 API는 스펙인데 엔티티를 반환하게되면 만약, 엔티티를 변경(ex. 필드[변수] 추가 등)하면
    //1. pw같은 중요 데이터가 노출이  2. API스펙이 변해버림. 엔티티 로직을 변경했는데 API 스펙이 변해버리기 떄문에 절대 쓰면 안됨
    //그니까 템플릿 엔진 같은 곳에만 쓰도록 하자
}
