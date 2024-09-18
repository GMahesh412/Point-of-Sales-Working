package com.pos.point_of_sales.entity;

/**
 * Represents a card payment in the Point of Sales system.
 *  * @author Mahesh Yadav
 */
public class CardPayment {
    private String id;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;

    /**
     * Default constructor for CardPayment.
     */
    public CardPayment() {
    }

    /**
     * Constructs a CardPayment with specified parameters.
     *
     * @param id          The ID of the card payment.
     * @param cardNumber  The card number.
     * @param expiryDate  The expiry date of the card.
     * @param cvv         The CVV of the card.
     * @param amount      The amount of the payment.
     */
    public CardPayment(String id, String cardNumber, String expiryDate, String cvv, double amount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    /**
     * Constructs a CardPayment with specified parameters.
     *
     * @param cardNumber  The card number.
     * @param expiryDate  The expiry date of the card.
     * @param cvv         The CVV of the card.
     * @param amount      The amount of the payment.
     */
    public CardPayment(String cardNumber, String expiryDate, String cvv, double amount) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
    }


    /**
     * Gets the ID of the card payment.
     *
     * @return The ID of the card payment.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the card payment.
     *
     * @param id The ID of the card payment.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the card number.
     *
     * @return The card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the card number.
     *
     * @param cardNumber The card number.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the expiry date of the card.
     *
     * @return The expiry date of the card.
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the card.
     *
     * @param expiryDate The expiry date of the card.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the CVV of the card.
     *
     * @return The CVV of the card.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV of the card.
     *
     * @param cvv The CVV of the card.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return The amount of the payment.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount The amount of the payment.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CardPayment{" +
                "id='" + id + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", amount=" + amount +
                '}';
    }
}
