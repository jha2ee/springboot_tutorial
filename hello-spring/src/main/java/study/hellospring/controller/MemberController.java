package study.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.hellospring.domain.Member;
import study.hellospring.service.MemberService;

import java.util.List;

//Spring 실행될 때 container가 객체를 생성하고 관리함
@Controller
public class MemberController {

    private final MemberService memberService; // 하나만 생성해서 공용으로 쓰려면 스프링 컨테이너에 등록하면 됨

    @Autowired
    // Defendency Injection
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }
    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
