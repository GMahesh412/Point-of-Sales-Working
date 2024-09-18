package com.pos.point_of_sales.entity;

/**
 * Represents a payment transaction in the Point of Sales system.
 */
public class PaymentTransaction {

    private String transactionId;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private double totalAmount;
    private String merchantName;
    private String orderNumber;
    private String paymentStatus;

    public PaymentTransaction() {
    }

    /**
     * Constructs a PaymentTransaction object with specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param cardNumber    The card number used for the transaction.
     * @param cvv           The CVV (Card Verification Value) of the card.
     * @param expiryDate    The expiry date of the card.
     * @param totalAmount   The total amount of the transaction.
     * @param merchantName  The name of the merchant or vendor.
     * @param orderNumber   The order number associated with the transaction.
     * @param paymentStatus The status of the payment transaction.
     */
    public PaymentTransaction(
            String transactionId, String cardNumber, String cvv, String expiryDate, double totalAmount,
            String merchantName, String orderNumber, String paymentStatus) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.totalAmount = totalAmount;
        this.merchantName = merchantName;
        this.orderNumber = orderNumber;
        this.paymentStatus = paymentStatus;
    }

    /**
     * Gets the transaction ID.
     *
     * @return The transaction ID.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     *
     * @param transactionId The transaction ID.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
     * Gets the CVV (Card Verification Value).
     *
     * @return The CVV.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the CVV (Card Verification Value).
     *
     * @param cvv The CVV.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * Gets the expiry date of the card.
     *
     * @return The expiry date.
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the card.
     *
     * @param expiryDate The expiry date.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the total amount of the transaction.
     *
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the transaction.
     *
     * @param totalAmount The total amount.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the name of the merchant or vendor.
     *
     * @return The merchant name.
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Sets the name of the merchant or vendor.
     *
     * @param merchantName The merchant name.
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Gets the order number associated with the transaction.
     *
     * @return The order number.
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the order number associated with the transaction.
     *
     * @param orderNumber The order number.
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Gets the payment status of the transaction.
     *
     * @return The payment status.
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the transaction.
     *
     * @param paymentStatus The payment status.
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", merchantName='" + merchantName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
