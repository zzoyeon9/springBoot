package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController //(@Controller + @ResponseBody : 데이터를 Json이나 XML로 바로 보낼 떄 사용 보내
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> memberV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        //강조 !! api를 만들때는 파라미터를 받든 안하든 절대 엔티티를 노출하거나 받지 말 것.
        //꼭 중간에 API 스펙에 맞는 DTO를 만들어서 활용하는 것을 강제!!할 것.(api 스펙이 바뀌거나
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/v1/members")//회원등록 API
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        //@Valid : validation이 자동으로 됨
        //@RequestBody : Json 데이터를 Member로 싹 바꿔줌
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);

//api 스펙을 위한 별도의 Dto를 만들어야함
//1. 화면마다 필요한 데이터가 다르며, 엔티티를 그대로 받아버리면 엔티티가 바뀔때마다 API스펙이 달라지기 떄문
//2. member의 어느 요소가 들어올지 모름
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {//api 스펙을 위한 별도의 Dto를 만들어야함 왜냐하면 화면마다 필요한 데이터가 다르며, 엔티티를 그대로 받아버리면 엔티티가 바뀔때마다 API스펙이 달라지기 떄문

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
//validation도 가능함
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
