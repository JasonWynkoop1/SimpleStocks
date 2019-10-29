package com.cs360.simplestocks.stock_api.inputForApi;

/**
 * The symbol parameter for the technical indicators/time series api call.
 */
public class Symbol implements ApiParameter {
    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getKey() {
        return "symbol";
    }

    @Override
    public String getValue() {
        return symbol;
    }
}