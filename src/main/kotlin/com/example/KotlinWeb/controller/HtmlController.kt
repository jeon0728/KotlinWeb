package com.example.KotlinWeb.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog" //import org.springframework.ui.set 으로 사용가능
        //model.addAttribute("title", "Blog") 위 코드와 같은 성격의 코드
        return "blog"
    }

}