package com.cs360.simplestocks.model;

import java.util.*;

public class Purchase {
    private Stock stock;
    private double quantity;
    private Date purchaseDate;

    public Purchase(Stock stock, double quantity, Date purchaseDate) {
        this.stock = stock;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
