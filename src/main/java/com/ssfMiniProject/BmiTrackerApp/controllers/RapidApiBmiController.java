package com.ssfMiniProject.BmiTrackerApp.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssfMiniProject.BmiTrackerApp.models.BmiRapidApiQuery;
import com.ssfMiniProject.BmiTrackerApp.models.RapidApiGetBmi;
import com.ssfMiniProject.BmiTrackerApp.services.BmiRapidApiService;

@Controller
@RequestMapping(path = "/rapidbmigenerated")
public class RapidApiBmiController {

    private static final Logger logger = LoggerFactory.getLogger(RapidApiBmiController.class);

    @Autowired
    private BmiRapidApiService bmiRapidApiService;

    @GetMapping
    public String rapidbmigenerated(@RequestParam(required = true) Double weight,
            @RequestParam(required = true) Double height,
            Model model) {
                
        BmiRapidApiQuery bmiRapidApiQuery = new BmiRapidApiQuery();
        bmiRapidApiQuery.setWeight(weight);
        bmiRapidApiQuery.setHeight(height);

        Optional<RapidApiGetBmi> optRapidApiGetBmi = bmiRapidApiService.generateUserBmi(bmiRapidApiQuery);
        if (optRapidApiGetBmi.isEmpty()) {
            model.addAttribute("rapidApiGetBmi", new RapidApiGetBmi());
            return "rapidbmigenerated";
        }

        logger.info("<<<<<" + bmiRapidApiQuery.getWeight() + "****" + bmiRapidApiQuery.getHeight());
        model.addAttribute("rapidApiGetBmi", optRapidApiGetBmi.get());
        model.addAttribute("weightService", bmiRapidApiQuery.getWeight());
        model.addAttribute("heightService", bmiRapidApiQuery.getHeight());
        return "rapidbmigenerated";
    }

}
