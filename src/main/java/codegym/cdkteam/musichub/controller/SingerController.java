package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.Singer;
import codegym.cdkteam.musichub.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ModelAndView detailSinger(@PathVariable Long id) {
        Optional<Singer> singer = singerService.findById(id);
        ModelAndView modelAndView;
        if (!singer.isPresent()) {
            modelAndView = new ModelAndView("404");
            return modelAndView;
        }
        Singer singers = singerService.findById(id).get();
        modelAndView = new ModelAndView("singer/detail");
        modelAndView.addObject("singer", singer.get());
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView editSinger(@PathVariable Long id){
        Singer singer = singerService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("singer/edit");
        modelAndView.addObject("singer", singer);
        return modelAndView;
    }
    @PostMapping("/edit")
    public ModelAndView saveSinger(@ModelAttribute("singer") Singer singer, RedirectAttributes redirect){
        singerService.save(singer);
        ModelAndView modelAndView = new ModelAndView("redirect:/singers");
        redirect.addFlashAttribute("message","Edit singer information successfully");
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView deleteSinger(@PathVariable Long id, RedirectAttributes redirectAttributes){
        singerService.remove(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/singers");
        redirectAttributes.addFlashAttribute("message", "successfully deleted");
        return modelAndView;

    }
}
