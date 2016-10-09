package com.b.exchangesync.utils;



import com.b.exchangesync.persistence.models.Quote;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.String;import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/15/16
 * Time: 4:13 AM
 */
public class QuoteUtils {

    public static List<Quote> buildQuotesFromString(String contents, long timestamp) {
        List<Quote> quotes = new ArrayList<Quote>();

        JSONObject jsonObject = new JSONObject(contents);
        JSONArray auctionsArray = jsonObject.getJSONObject("list").getJSONArray("resources");

        for (int i = 0; i < auctionsArray.length(); i++) {
            JSONObject fields = ((JSONObject) auctionsArray.get(i)).getJSONObject("resource").getJSONObject("fields");


            Quote quote = new Quote();
            quote.setName(fields.getString("name"));
            quote.setSymbol(fields.getString("symbol"));
            quote.setPrice(fields.getString("price"));
            quote.setTs(fields.getLong("ts"));
            quote.setVolume(fields.getLong("volume"));
            quote.setType(fields.getString("type"));
            quote.setUtctime(fields.getString("utctime"));
            quote.setDate(DateUtils.getDayStringFromLong(quote.getTs()));

            quotes.add(quote);
        }


        return quotes;
    }

}
