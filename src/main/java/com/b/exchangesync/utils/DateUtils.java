package com.b.exchangesync.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/20/16
 * Time: 4:08 AM
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String YYYY_MMDD = "yyyyMMdd";

    public static String getDayStringFromLong(long ts) {
        Date dt = new Date(ts*1000);
        DateFormat sdf = new SimpleDateFormat(YYYY_MMDD);
        return sdf.format(dt);

    }




}
