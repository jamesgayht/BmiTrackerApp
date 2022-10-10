package com.ssfMiniProject.BmiTrackerApp.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssfMiniProject.BmiTrackerApp.models.GeneratedBmiObj;

@Service
public class BmiGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(BmiGeneratorService.class);

    String apiKey = System.getenv("BMI_API_KEY");

    private static final String URL = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric";
    
    public Optional<GeneratedBmiObj> getGeneratedBmi (String weight, String height) {
        
        logger.info("APIKEY > " + apiKey);
        String requestUrl = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("weight", weight)
                            .queryParam("height", height)
                            .toUriString();

        logger.info("Request URL > " + requestUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(requestUrl, 
                                    HttpMethod.GET, 
                                    request, 
                                    String.class,
                                    1);

            GeneratedBmiObj generatedBmi = GeneratedBmiObj.createJson(resp.getBody());
            logger.info("response body > " + resp.getBody());
            return Optional.of(generatedBmi);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.info("response empty");
        return Optional.empty();
    }
}
