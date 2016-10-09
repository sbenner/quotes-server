package com.b.exchangesync.service.services;

import com.b.exchangesync.service.persistence.models.Quote;
import com.b.exchangesync.service.persistence.repositories.QuoteRepository;
import com.b.exchangesync.service.utils.QuoteUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/15/16
 * Time: 4:05 AM
 */

@Service
public class ExchangeInfoHttpProcessor extends TimerTask {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExchangeInfoHttpProcessor.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    QuoteRepository quoteRepository;

    public static HttpEntity<String> entity;


    //Looks weird? not at all
    // see http://stackoverflow.com/questions/38355075/has-yahoo-finance-web-service-disappeared-api-changed-down-temporarily
    @PostConstruct
    public void init(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent","Mozilla/5.0 (Linux; Android 6.0; MotoE2(4G-LTE) Build/MPI24.65-39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36");
        entity = new HttpEntity<String>("parameters", headers);

    }


    public String getExchangeData() {
        String url = "http://finance.yahoo.com/webservice/v1/symbols/USDRUB=X,EURRUB=X,BZH16.NYM,BZG16.NYM,BZZ16.NYM,BZK16.NYM,BZZ17.NYM,BZJ16.NYM,CLG16.NYM/quote?format=json";
        String res = null;
        try {
            res = restTemplate.exchange(url, HttpMethod.GET, entity,String.class).getBody();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;

    }

    @Override
    public void run() {
        String res = getExchangeData();
        if (res != null) {
            List<Quote> quotesList = QuoteUtils.buildQuotesFromString(res, 0);
            quoteRepository.save(quotesList);
        }

    }
}
