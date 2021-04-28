package com.yafnds.springbootshiro.controller;

import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 包名称： com.yafnds.springbootshiro.controller
 * 类名称：MyController
 * 类描述：MyController
 * 创建人：@author y19991
 * 创建时间：2021/4/25 16:47
 */

@Controller
public class MyController {

    @Resource
    private UserService userService;

    @RequestMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg","hello,Shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {

        // 获取一个用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 执行登录的方法
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            // 用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("toRegister")
    public String toRegister() { return "/register"; }

    @RequestMapping("/register")
    public String register(User user) {

        try {
            userService.save(user);
            return "redirect:/toLogin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/toRegister";
        }
    }

    /**
     * 注销
     * @return 重定向至登录页
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/toLogin";
    }

    /**
     * 未授权
     * @return 错误信息：“未经授权，无法访问此页面”
     */
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized() { return "未经授权，无法访问此页面"; }
}
