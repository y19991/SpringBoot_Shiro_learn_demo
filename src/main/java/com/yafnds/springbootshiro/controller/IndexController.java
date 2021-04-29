package com.yafnds.springbootshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 包名称：com.yafnds.springbootshiro.controller
 * 类名称：IndexController
 * 类描述：主页控制器
 * 创建人：@author y19991
 * 创建时间：2021/4/29 17:16
 */

@Controller
public class IndexController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg","hello,Shiro");
        return "index";
    }
}
