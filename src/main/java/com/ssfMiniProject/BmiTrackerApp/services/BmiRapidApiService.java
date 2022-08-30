package com.ssfMiniProject.BmiTrackerApp.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssfMiniProject.BmiTrackerApp.models.BmiRapidApiQuery;
import com.ssfMiniProject.BmiTrackerApp.models.RapidApiGetBmi;

@Service
public class BmiRapidApiService {

    private static final Logger logger = LoggerFactory.getLogger(BmiRapidApiService.class);

    private static String BMI_GENERATOR = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric";

    public Optional<RapidApiGetBmi> generateUserBmi (BmiRapidApiQuery bmiRapidApiQuery) {

        String rapidApiKey = "RAPID_KEY"; //set in env
        String rapidApiHost = "RAPIDAPI_HOST"; // set in env

        String bmiRapidApiUrl = UriComponentsBuilder.fromUriString(BMI_GENERATOR)
                .queryParam("weight", bmiRapidApiQuery.getWeight())
                .queryParam("height", bmiRapidApiQuery.getHeight())
                .toUriString();

        logger.info(bmiRapidApiUrl);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-RapidAPI-Key", rapidApiKey);
            httpHeaders.set("X-RapidAPI-Host", rapidApiHost);

            HttpEntity requestHttpEntity = new HttpEntity(httpHeaders);
            responseEntity = restTemplate.exchange(
                    bmiRapidApiUrl,
                    HttpMethod.GET,
                    requestHttpEntity,
                    String.class);

            logger.info(responseEntity.getBody());
            
            RapidApiGetBmi rapidApiGetBmi = RapidApiGetBmi.createJson(responseEntity.getBody());
            return Optional.of(rapidApiGetBmi);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
