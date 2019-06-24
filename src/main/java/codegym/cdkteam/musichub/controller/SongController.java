package codegym.cdkteam.musichub.controller;

import codegym.cdkteam.musichub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SongController {
  @Autowired
  SongService songService;
}
