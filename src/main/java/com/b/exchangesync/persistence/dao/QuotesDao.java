package com.b.exchangesync.persistence.dao;

import com.b.exchangesync.persistence.models.Quote;
import com.b.exchangesync.utils.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/18/16
 * Time: 5:25 PM
 */

@Component
public class QuotesDao extends MongoTemplate {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuotesDao.class);
    @Autowired
    MongoDbFactory mongoDbFactory;

    @Autowired
    public QuotesDao(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    public void updateQuote(Quote quote) {
        Query q = new Query(where("_id").is(quote.get_id()));
        Update u = new Update();
        logger.info(quote.get_id());
        u.set("date", DateUtils.getDayStringFromLong(quote.getTs()));
        this.upsert(q, u, Quote.class);
    }


}
