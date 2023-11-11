package datajpa.study.controller;

import datajpa.study.entity.Team;
import datajpa.study.repository.MemberJpaRepository;
import datajpa.study.repository.TeamJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/teams/{teamId}")
    public String method4(@PathVariable Long teamId, Model model) {
        Optional<Team> team1 = teamJpaRepository.findById(teamId);
        if(team1.isPresent()) {
            Team team = team1.get();
            model.addAttribute("team",team);
            model.addAttribute("members",team.getMembers());
        }
        return "/team/team";
    }
    @GetMapping("/{teamId}/edit")
    public String method5(@PathVariable Long teamId, Model model) {
        Optional<Team> team1 = teamJpaRepository.findById(teamId);
        if (team1.isPresent()) {
            Team team = team1.get();
            model.addAttribute("team",team);
        }
        return "/team/editForm";
    }
    @PostMapping("/{teamId}/edit")
    @Transactional
    public String method6(@PathVariable Long teamId, @ModelAttribute Team team) {
        Team findTeam = teamJpaRepository.findById(teamId).orElseThrow();
        findTeam.setName(team.getName());
        return "redirect:/team/teams/{teamId}";
    }
    @PostMapping("/{teamId}/delete")
    @Transactional
    public String method7(@PathVariable Long teamId){
        teamJpaRepository.deleteById(teamId);
        return "redirect:/team/teams";
    }


}
