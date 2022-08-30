package com.ssfMiniProject.BmiTrackerApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ssfMiniProject.BmiTrackerApp.models.UserBmi;
import com.ssfMiniProject.BmiTrackerApp.services.UserRedis;

@Controller
public class UserBmiRedisController {
    @Autowired
    UserRedis service;

    @GetMapping("/")
    public String userBmiForm(Model model) {
        model.addAttribute("userbmi", new UserBmi());
        return "userbmi";
    }

    @GetMapping("/userbmi/{id}")
    public String getUser (Model model, @PathVariable(value="id") String id) {
        UserBmi loadUserBmi = service.findById(id);
        model.addAttribute("userbmi", loadUserBmi);
        return "showUserBmi";
    }

    @PostMapping("/userbmi")
    public String submitUserBmi(@ModelAttribute UserBmi userBmi, Model model) {
        UserBmi saveUserBmi = new UserBmi(
                userBmi.getName(),
                userBmi.getEmail(),
                userBmi.getHeight(),
                userBmi.getWeight());
                service.save(saveUserBmi);
        model.addAttribute("userbmi", saveUserBmi);
        return "showUserBmi";
    }
}
