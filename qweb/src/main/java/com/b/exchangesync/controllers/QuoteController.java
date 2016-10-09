package com.b.exchangesync.controllers;

import com.b.exchangesync.service.persistence.models.Quote;
import com.b.exchangesync.service.persistence.repositories.QuoteRepository;
import com.b.exchangesync.service.utils.Period;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/15/16
 * Time: 5:41 PM
 */

@Controller
public class QuoteController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    QuoteRepository quoteRepository;

    @RequestMapping(value = "/quotes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Quote>> getQuotes(HttpServletResponse res, HttpServletRequest req,
                                                 @RequestParam(value = "symbol", required = true) String symbol,
                                                 @RequestParam(value = "date", required = false) String date,
                                                 @RequestParam(value = "period", required = false) String period) throws IOException {
        res.addHeader("Content-Type", "application/json;charset=utf-8");
        try {

            Sort sort = new Sort(Sort.Direction.DESC, "ts");
            logger.info("query str: "+req.getQueryString());

            if (period != null) {
                Period p = Period.valueOf(period);
                logger.info("period: "+p.getSeconds());
                long ts = p.getSeconds();
                ts=System.currentTimeMillis()/1000-ts;

                return new ResponseEntity<>(quoteRepository.findQuoteBySymbolGtThanTs(symbol, ts, sort), HttpStatus.OK);
            } else if (date != null) {
                return new ResponseEntity<>(quoteRepository.findQuoteBySymbolAndDate(symbol, date, sort), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
