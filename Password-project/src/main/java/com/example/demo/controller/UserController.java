package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("register")
    public String RegisterUser( User user){
        return "Register";
    }

    @RequestMapping("view")
    public String Users(Model model){
        model.addAttribute("user",this.userRepository.findAll());

        return "index";
    }

    @RequestMapping("add")
    public String addUser(User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "Register";
        }
        this.userRepository.save(user);
        return "redirect:/user/view";
    }

    @RequestMapping("updated/{id}")
    public String showUpdateUser(@PathVariable("UserId") long UserId, Model model){
        User user = this.userRepository.findById(UserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + UserId));
        model.addAttribute("user",user);

        return "update";
    }


    @RequestMapping("update/{id}")
    public String updateUser(@PathVariable("UserId") long UserId, User user, BindingResult result, Model model){
        if(result.hasErrors()){
            user.setUserId(UserId);
            return "update";
        }

        //update user
        userRepository.save(user);

        //get all users with update
        model.addAttribute("user",this.userRepository.findAll());
        return "index";
    }

    @RequestMapping("delete/{Id}")
    public String deleleUser(@PathVariable("UserId") long UserId, Model model){
        User user = this.userRepository.findById(UserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + UserId));
        model.addAttribute("user",user);

        this.userRepository.delete(user);
        model.addAttribute("user",this.userRepository.findAll());
        return "index";
    }
}
