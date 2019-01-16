package com.igomall.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp")
public class IndexController{

    @GetMapping({"/","/*","/**"})
   public String index(){
        return "/admin/erp/index";
   }
}
