package cn.cnwair.mes.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class Security {
    @GetMapping("/login")
    public String login(){
        return "security/login";
    }
}
