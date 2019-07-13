package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Singer;
import codegym.cdkteam.musichub.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping("")
    public ModelAndView listSinger(){
        List<Singer> singers = singerService.findAll();
        ModelAndView modelAndView = new ModelAndView("singer/list");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createSinger(){
        ModelAndView modelAndView = new ModelAndView("singer/create");
        modelAndView.addObject("singer", new Singer());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView addNewSinger(@ModelAttribute("singer") Singer singer){
        singerService.save(singer);
        ModelAndView modelAndView = new ModelAndView("singer/create");
        modelAndView.addObject("singer", new Singer());
        modelAndView.addObject("message", "Create a successful singer");
        return modelAndView;
    }
}
