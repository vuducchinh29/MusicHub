package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Singer;
import codegym.cdkteam.musichub.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping("")
    public ModelAndView listSinger(@RequestParam("s") Optional<String> s){
        List<Singer> singers;
        if(s.isPresent()){
            singers = singerService.findAllByNameContaining(s.get());
        } else {
            singers = singerService.findAll();

        }
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
}
