package com.hblolj.haichat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hblolj
 * @Date: 2018/9/26 9:49
 * @Description:
 * @Version: 1.0
 **/
@RestController
public class AccountController {

    @GetMapping("/login")
    public String get(){
        return "request the login way";
    }
}
