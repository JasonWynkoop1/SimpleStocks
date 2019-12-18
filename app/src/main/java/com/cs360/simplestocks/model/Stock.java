package com.cs360.simplestocks.model;

import java.util.Date;

/**
 * Creates a stock object
 */

public class Stock {
    private String companyName;
    private String symbol;
    private String stockExchange;
    private double price;
    private double priceChange;
    private double percentChange;
    private double previousClose;
    private double open;
    private double[] daysRange;
    private double[] yearsRange;
    private Date earningsDate;
    private double forwardDividend;
    private double forwardYield;
    private Date exDividendDate;
    private double oneYearTargetEst;

    public Stock(String companyName, String symbol, String stockExchange, double price, double priceChange,
                 double percentChange, double previousClose, double open, double[] daysRange,
                 double[] yearsRange, Date earningsDate, double forwardDividend, double forwardYield,
                 Date exDividendDate, double oneYearTargetEst) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.stockExchange = stockExchange;
        this.price = price;
        this.priceChange = priceChange;
        this.percentChange = percentChange;
        this.previousClose = previousClose;
        this.open = open;
        this.daysRange = daysRange;
        this.yearsRange = yearsRange;
        this.earningsDate = earningsDate;
        this.forwardDividend = forwardDividend;
        this.forwardYield = forwardYield;
        this.exDividendDate = exDividendDate;
        this.oneYearTargetEst = oneYearTargetEst;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double[] getDaysRange() {
        return daysRange;
    }

    public void setDaysRange(double[] daysRange) {
        this.daysRange = daysRange;
    }

    public double[] getYearsRange() {
        return yearsRange;
    }

    public void setYearsRange(double[] yearsRange) {
        this.yearsRange = yearsRange;
    }

    public Date getEarningsDate() {
        return earningsDate;
    }

    public void setEarningsDate(Date earningsDate) {
        this.earningsDate = earningsDate;
    }

    public double getForwardDividend() {
        return forwardDividend;
    }

    public void setForwardDividend(double forwardDividend) {
        this.forwardDividend = forwardDividend;
    }

    public double getForwardYield() {
        return forwardYield;
    }

    public void setForwardYield(double forwardYield) {
        this.forwardYield = forwardYield;
    }

    public Date getExDividendDate() {
        return exDividendDate;
    }

    public void setExDividendDate(Date exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    public double getOneYearTargetEst() {
        return oneYearTargetEst;
    }

    public void setOneYearTargetEst(double oneYearTargetEst) {
        this.oneYearTargetEst = oneYearTargetEst;
    }
}
