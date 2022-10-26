package com.goodjob;

import com.goodjob.company.repository.RegionRepository;
import com.goodjob.company.service.CompanyService;
import com.goodjob.post.occupation.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final OccupationService occuService;
    private final CompanyService companyService;

    @GetMapping("/")
    public String main(){
        return "mainPage";
    }

    @GetMapping("/login")
    public String login() { return "login";}

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("occuAll",occuService.searchOccName());
        model.addAttribute("regName",companyService.searchRegName());
        return "searchPage";}

}

