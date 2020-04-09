package com.demo.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉
 *
 * @author wanghu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/")
public class AdminController {

    @GetMapping("init")
    public String init() {
        return "index";
    }

    @GetMapping("loginIndex")
    public String login() {
        return "login";
    }

    @GetMapping("error")
    public String error() {
        return "error";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }
}
