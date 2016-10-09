package com.b.exchangesync.utils;

import com.b.exchangesync.persistence.dao.QuotesDao;
import com.b.exchangesync.persistence.models.Quote;
import com.b.exchangesync.persistence.repositories.QuoteRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/20/16
 * Time: 4:40 AM
 */
public class UpdateQuotesWithDay {



    public static void main(String[] args) {

      ApplicationContext apctx  =  new ClassPathXmlApplicationContext("servicecontext.xml");

        QuotesDao quotesDao =  (QuotesDao)apctx.getBean("quotesDao");
        QuoteRepository quoteRepository =  (QuoteRepository)apctx.getBean("quoteRepository");
        Page<Quote> quoteList =
                quoteRepository.findAllWithoutDate(new PageRequest(0,20));

        while(quoteList.hasContent()){
            System.out.println("updating page "+quoteList.getNumber());
            updateQuote(quotesDao,quoteList.getContent());
            quoteList = quoteRepository.findAllWithoutDate(new PageRequest(0,20));
        }

    }

    public static List<Quote> updateQuote(QuotesDao quotesDao, List<Quote> quotes){
        List<Quote> newQuotes = new ArrayList<Quote>();
        newQuotes.addAll(quotes);
        for(Quote q: newQuotes)
        {
            quotesDao.updateAuctionsUrl(q);
        }

        return newQuotes;

    }


}
