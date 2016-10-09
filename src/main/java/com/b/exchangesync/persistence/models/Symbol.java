package com.b.exchangesync.persistence.models;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 1/23/16
 * Time: 5:44 AM
 */
public class Symbol {

    private String symbol;
    private String name;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
