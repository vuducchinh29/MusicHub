package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.model.SingerDTO;
import codegym.cdkteam.musichub.model.UserDTO;
import codegym.cdkteam.musichub.model.song.SongDTO;
import codegym.cdkteam.musichub.service.SingerService;
import codegym.cdkteam.musichub.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class SingerController {
    @Autowired
    private SingerService singerService;

    @Autowired
    private UserDTOService userService;

    @GetMapping("/allsingers")
    public ModelAndView listSinger(){
        List<SingerDTO> singers = singerService.findAll();
        ModelAndView modelAndView = new ModelAndView("singer/list");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/singers")
    public ModelAndView Singer() {
        List<SingerDTO> singers = singerService.findAll();
        ModelAndView modelAndView = new ModelAndView("singer/singers");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/singers/add")
    public ModelAndView createSinger(){
        ModelAndView modelAndView = new ModelAndView("singer/create");
        modelAndView.addObject("singer", new SingerDTO());
        return modelAndView;
    }
    @PostMapping("/singers/add")
    public ModelAndView addNewSinger(@ModelAttribute("singer") SingerDTO singerDTO, RedirectAttributes redirectAttributes){
        singerService.save(singerDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/singers/add");
        redirectAttributes.addFlashAttribute("singer", new SingerDTO());
        redirectAttributes.addFlashAttribute("message", "Successful!");
        return modelAndView;
    }
    @GetMapping("/singers/{id}")
    public ModelAndView detailSinger(@PathVariable Long id) {
        Optional<SingerDTO> singer = singerService.findById(id);
        List<SongDTO> songs = singer.get().getSongs();
        List<SingerDTO> singers = singerService.findAll();
        ModelAndView modelAndView;
        if (!singer.isPresent()) {
            modelAndView = new ModelAndView("404");
            return modelAndView;
        }
            modelAndView = new ModelAndView("singer/detail");
            modelAndView.addObject("singer", singer.get());
            modelAndView.addObject("songs", songs);
            modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/singers/update/{id}")
    public ModelAndView editSinger(@PathVariable Long id){
        SingerDTO singerDTO = singerService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("singer/edit");
        modelAndView.addObject("singer", singerDTO);
        return modelAndView;
    }
    @PostMapping("/singers/update")
    public ModelAndView saveSinger(@ModelAttribute("singer") SingerDTO singerDTO, RedirectAttributes redirect){
        singerService.save(singerDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/singers/update/" + singerDTO.getId());
        redirect.addFlashAttribute("message","Update successful!");
        return modelAndView;
    }
    @GetMapping("/singers/delete/{id}")
    public ModelAndView deleteSinger(@PathVariable Long id, RedirectAttributes redirectAttributes){
        singerService.remove(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/singers");
        redirectAttributes.addFlashAttribute("message", "Delete successful!");
        return modelAndView;

    }
}
