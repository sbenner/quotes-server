package com.b.exchangesync.services;

import com.b.exchangesync.models.Quote;
import com.b.exchangesync.repositories.QuoteRepository;
import com.b.exchangesync.utils.ExchangeUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/15/16
 * Time: 4:05 AM
 */

@Service
public class ExchangeInfoHttpService extends TimerTask {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExchangeInfoHttpService.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    QuoteRepository quoteRepository;

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    public String getExchangeData() {
        String url = "http://finance.yahoo.com/webservice/v1/symbols/USDRUB=X,EURRUB=X,BZH16.NYM,BZG16.NYM,BZZ16.NYM,BZK16.NYM,BZZ17.NYM,BZJ16.NYM,CLG16.NYM/quote?format=json";
        String res = null;
        try {
            res = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;

    }

    @Override
    public void run() {
        String res = getExchangeData();
        if (res != null) {
            List<Quote> quotesList = ExchangeUtils.buildQuotesFromString(res, 0);
            quoteRepository.save(quotesList);
        }

    }
}
