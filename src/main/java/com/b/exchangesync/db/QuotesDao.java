package com.b.exchangesync.db;

import com.b.exchangesync.models.Quote;
import com.b.exchangesync.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/18/16
 * Time: 5:25 PM
 */

@Component
public class QuotesDao extends MongoTemplate {
    @Autowired
    MongoDbFactory mongoDbFactory;



    @Autowired
    public QuotesDao(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);

    }


    public void updateAuctionsUrl(Quote quote) {
       Query q = new Query(where("_id").is(quote.get_id()));
        Update u = new Update();
        System.out.println(quote.get_id());
        u.set("date", DateUtils.getDayStringFromLong(quote.getTs()));
        this.upsert(q, u, Quote.class);
    }








}
