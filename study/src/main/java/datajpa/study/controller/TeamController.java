package datajpa.study.controller;

import datajpa.study.entity.Team;
import datajpa.study.repository.MemberJpaRepository;
import datajpa.study.repository.TeamJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamJpaRepository teamJpaRepository;

    @GetMapping("/teams")
    public String method1(Model model) {
        List<Team> teams = teamJpaRepository.findAll();
        model.addAttribute("teams",teams);
        return "/team/teams";
    }
    @GetMapping("/addForm")
    public String method2() {return "/team/addForm";}

    @PostMapping("/add")
    @Transactional
    public String method3(Team team) {
        Team savedTeam = teamJpaRepository.save(team);
        return "redirect:/team/teams";
    }

}
