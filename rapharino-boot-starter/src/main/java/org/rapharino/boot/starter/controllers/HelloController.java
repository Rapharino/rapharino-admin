package org.rapharino.boot.starter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Rapharino on 2016/11/4.
 */
@Controller
public class HelloController {
    @GetMapping("/")
    public String helloHtml(Model model){
        model.addAttribute("para","hello rapharino form georg");
        return "hello";
    }
}
