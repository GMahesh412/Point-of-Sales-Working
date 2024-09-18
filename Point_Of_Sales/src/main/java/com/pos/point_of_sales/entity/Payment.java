package com.pos.point_of_sales.entity;

/**
 * Represents a payment in the Point of Sales system.
 * @author Mahesh Yadav
 */
public class Payment {

    private double subTotal;
    private double sgst;
    private double cgst;
    private double discount;
    private double payable;

    /**
     * Constructs a Payment object with specified parameters.
     *
     * @param subTotal  The subtotal amount of the payment.
     * @param sgst      The SGST (State Goods and Services Tax) amount.
     * @param cgst      The CGST (Central Goods and Services Tax) amount.
     * @param discount  The discount applied to the payment.
     * @param payable   The total payable amount after applying taxes and discounts.
     */
    public Payment(double subTotal, double sgst, double cgst, double discount, double payable) {
        this.subTotal = subTotal;
        this.sgst = sgst;
        this.cgst = cgst;
        this.discount = discount;
        this.payable = payable;
    }

    /**
     * Gets the subtotal amount of the payment.
     *
     * @return The subtotal amount.
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the subtotal amount of the payment.
     *
     * @param subTotal The subtotal amount.
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Gets the SGST (State Goods and Services Tax) amount.
     *
     * @return The SGST amount.
     */
    public double getSgst() {
        return sgst;
    }

    /**
     * Sets the SGST (State Goods and Services Tax) amount.
     *
     * @param sgst The SGST amount.
     */
    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    /**
     * Gets the CGST (Central Goods and Services Tax) amount.
     *
     * @return The CGST amount.
     */
    public double getCgst() {
        return cgst;
    }

    /**
     * Sets the CGST (Central Goods and Services Tax) amount.
     *
     * @param cgst The CGST amount.
     */
    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    /**
     * Gets the discount applied to the payment.
     *
     * @return The discount amount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount applied to the payment.
     *
     * @param discount The discount amount.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets the total payable amount after applying taxes and discounts.
     *
     * @return The total payable amount.
     */
    public double getPayable() {
        return payable;
    }

    /**
     * Sets the total payable amount after applying taxes and discounts.
     *
     * @param payable The total payable amount.
     */
    public void setPayable(double payable) {
        this.payable = payable;
    }
}
