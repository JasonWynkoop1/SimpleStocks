package com.cs360.simplestocks.model;

import java.util.*;

public class CreditCard {
    private String cardNumber;
    private String nameOnCard;
    private Date expirationDate;
    private String securityCode;

    public CreditCard(String nameOnCard, String cardNumber,
                      Date expirationDate, String securityCode) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * Check to see whether credit card is expired.
     * @return - true if expired, false if not expired.
     */
    public boolean isExpired(){
        Date currentDate = new Date();

        if(currentDate.after(expirationDate)){
            return true;
        }
        else
        {
            return false;
        }
    }
}
