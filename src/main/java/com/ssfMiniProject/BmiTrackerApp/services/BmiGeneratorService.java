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

import com.ssfMiniProject.BmiTrackerApp.models.GeneratedBmiObj;

@Service
public class BmiGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(BmiGeneratorService.class);

    String apiKey = "a0bde9d50dmsh39a261972415d2dp1ddf80jsn13b950dd7ead";
    private static final String URL = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric";

    public Optional<GeneratedBmiObj> getGeneratedBmi (String weight, String height) {

        String requestUrl = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("weight", weight)
                            .queryParam("height", height)
                            .toUriString();

        logger.info(requestUrl);
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
