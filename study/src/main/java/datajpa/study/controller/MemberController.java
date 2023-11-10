package datajpa.study.controller;

import datajpa.study.entity.Member;
import datajpa.study.entity.Team;
import datajpa.study.repository.MemberJpaRepository;
import datajpa.study.repository.MemberRepository;
import datajpa.study.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TeamJpaRepository teamJpaRepository;

    @GetMapping("/members")
    public String method1(Model model){
        List<Member> members = memberJpaRepository.findAll();
        model.addAttribute("members",members);
        return "member/members";
    }
    @GetMapping("/addForm")
    public String method2(Model model){
        List<Team> teams = teamJpaRepository.findAll();
        model.addAttribute("teams", teams);
        return "member/addForm";
    }
    @PostMapping("/add")
    @Transactional
    public String method3(Member member) {
        Member savedMember = memberJpaRepository.save(member);
        return "redirect:/member/members";
    }

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }
}
