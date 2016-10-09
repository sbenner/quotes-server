package com.b.exchangesync.service.utils;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/23/16
 * Time: 2:39 AM
 */


/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/23/16
 * Time: 2:37 AM
 */
public enum Period {
    day(3600*24),
    seven_days(86400 * 7),
    month(86400 * 30),
    six_months(86400 * 180),
    year(86400 * 365);

    private long seconds;

    Period(long milliseconds) {
        this.setSeconds(milliseconds);
    }

      public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}