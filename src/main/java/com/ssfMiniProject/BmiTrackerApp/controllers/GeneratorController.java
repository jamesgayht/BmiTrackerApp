package com.ssfMiniProject.BmiTrackerApp.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssfMiniProject.BmiTrackerApp.models.BmiData;
import com.ssfMiniProject.BmiTrackerApp.models.DayObj;
import com.ssfMiniProject.BmiTrackerApp.models.GeneratedBmiObj;
import com.ssfMiniProject.BmiTrackerApp.models.User;
import com.ssfMiniProject.BmiTrackerApp.services.BmiGeneratorService;
import com.ssfMiniProject.BmiTrackerApp.services.UserRedisService;

@Controller
public class GeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorController.class);

    @Autowired
    private BmiGeneratorService bmiGeneratorService;

    @Autowired
    private User currUser;

    @Autowired
    private UserRedisService redisService;

    @Autowired
    private DayObj dayObj;

    @Autowired
    private GeneratedBmiObj currGeneratedBmiObj;

    String currDay;

    @RequestMapping("/")
    public String showIndexPage(Model model) {
        currUser = new User();
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam(required = true) String username, String password, Model model) {

        Optional<User> optUser = redisService.getByUsername(username);
        if (optUser.isEmpty()) {
            currUser = new User();
            currUser.setUsername(username);
            currUser.setPassword(password);
        } else {
            currUser = optUser.get();
        }
        if (currUser.getDayMap().containsKey(dayObj.day)) {
            currGeneratedBmiObj = currUser.getDayMap().get(dayObj.day).getDailyBmi();
            model.addAttribute("generatedBmiIsPresent", "true");
            model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        } else {
            model.addAttribute("generatedBmiObj", "emptyList");
            model.addAttribute("generatedBmiIsPresent", "false");
        }
        dayObj.setDailyBmi(currGeneratedBmiObj);
        currDay = dayObj.day;
        logger.info(dayObj.day);
        model.addAttribute("currUser", currUser);
        model.addAttribute("dayObj", dayObj);

        return "main";
    }

    @PostMapping("/showBmi/{username}")
    public String showBmi(@RequestParam(required = true) String queryWeight,
            @RequestParam(required = true) String queryHeight, @RequestParam(required = true) String day,
            Model model) {

        logger.info(day);
        logger.info(dayObj.day);

        if (!dayObj.getDay().equals(day)) {
            logger.info("new day");
            if (currUser.getDayMap().containsKey(day)) {
                currGeneratedBmiObj = currUser.getDayMap().get(day).dailyBmi;
            } else {
                currGeneratedBmiObj = new GeneratedBmiObj();
            }
            dayObj.setDay(day);
        }

        logger.info(dayObj.day);
        currDay = dayObj.day;
        Optional<GeneratedBmiObj> optGeneratedBmiObj = bmiGeneratorService.getGeneratedBmi(queryWeight, queryHeight);

        model.addAttribute("dayObj", dayObj);
        model.addAttribute("currUser", currUser);

        if (optGeneratedBmiObj.isEmpty()) {
            model.addAttribute("generatedBmiObj", new GeneratedBmiObj());
            return "showBmi";
        }
        model.addAttribute("generatedBmiObj", optGeneratedBmiObj.get());
        logger.info("adding dayObj > " + dayObj.day);
        return "showBmi";
    }

    @PostMapping(value = "/save", params = "saveSelected")
    public String addBmi(@ModelAttribute GeneratedBmiObj generatedBmiObj, @ModelAttribute DayObj dayObj, Model model) {

        for (BmiData bmiData : generatedBmiObj.getBmiList()) {
            currGeneratedBmiObj.getBmiList().add(bmiData);
            logger.info("/save bmiData.bmi > " + bmiData.getUserBmi());
            logger.info("/save bmiData.weight > " + bmiData.getUserWeight());
            logger.info("/save bmiData.height > " + bmiData.getUserHeight());
        }

        dayObj.setDailyBmi(currGeneratedBmiObj);
        currUser.addDay(dayObj);
        redisService.save(currUser);

        model.addAttribute("currUser", currUser);
        model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        model.addAttribute("dayObj", dayObj);

        return "main";
    }

    @GetMapping("/cancel")
    public String cancel(@ModelAttribute DayObj dayObj, Model model) {
        dayObj.setDay(currDay);
        logger.info("cancel day > " + dayObj.day);

        model.addAttribute("currUser", currUser);
        model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        model.addAttribute("dayObj", dayObj);

        return "main";
    }

    @GetMapping("/home")
    public String home (@ModelAttribute DayObj dayObj, Model model) {
        dayObj.setDay(currDay);
        logger.info("cancel day > " + dayObj.day);

        model.addAttribute("currUser", currUser);
        model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        model.addAttribute("dayObj", dayObj);

        return "main";
    }


    @PostMapping(value = "/del/{username}/{day}", params = "delete")
    public String deleteAll(@PathVariable(name = "username", required = true) String username,
            @PathVariable(required = true) String day,
            @ModelAttribute GeneratedBmiObj generatedBmiObj, Model model) {

        User currUser = redisService.getByUsername(username).get();

        currUser.delDay(day);

        redisService.save(currUser);
        logger.info("deleted > " + username + " day >" + day);
        
        model.addAttribute("currUser", currUser);
        model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        model.addAttribute("dayObj", dayObj);

        return "main";
    }

    @GetMapping("/selectDay/{username}/{day}")
    public String selectDay(@PathVariable(name = "username", required = true) String username,
            @PathVariable(required = true) String day, Model model) {

        User currUser = redisService.getByUsername(username).get();
        GeneratedBmiObj currGeneratedBmiObj = currUser.getBmiObj(day);
        DayObj dayObj = new DayObj(day);
        logger.info("select > " + username + " day > " + day);

        model.addAttribute("currUser", currUser);
        model.addAttribute("generatedBmiObj", currGeneratedBmiObj);
        model.addAttribute("dayObj", dayObj);

        return "edit";
    }

}
