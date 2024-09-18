package com.pos.point_of_sales.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents an invoice in the Point of Sales system.
 * @author Mahesh_Yadav
 */
public class Invoice implements Serializable {
    private String id;
    private Employee employee;
    private double total;
    private double sgst;
    private double cgst;
    private double discount;
    private double payable;
    private double paid;
    private double returned;
    private Date datetime;

    /**
     * Default constructor for Invoice.
     */
    public Invoice() {
    }

    /**
     * Constructs an Invoice with specified parameters.
     *
     * @param id       The ID of the invoice.
     * @param employee The employee associated with the invoice.
     * @param total    The total amount of the invoice.
     * @param sgst     The SGST amount of the invoice.
     * @param cgst     The CGST amount of the invoice.
     * @param discount The discount applied to the invoice.
     * @param payable  The payable amount of the invoice.
     * @param paid     The amount paid for the invoice.
     * @param returned The returned amount for the invoice.
     * @param datetime The date and time of the invoice.
     */
    public Invoice(String id, Employee employee, double total, double sgst, double cgst, double discount, double payable, double paid, double returned, Date datetime) {
        this.id = id;
        this.employee = employee;
        this.total = total;
        this.sgst = sgst;
        this.cgst = cgst;
        this.discount = discount;
        this.payable = payable;
        this.paid = paid;
        this.returned = returned;
        this.datetime = datetime;
    }

    /**
     * Constructs an Invoice with specified parameters.
     *
     * @param id       The ID of the invoice.
     * @param employee The employee associated with the invoice.
     * @param total    The total amount of the invoice.
     * @param sgst     The SGST amount of the invoice.
     * @param cgst     The CGST amount of the invoice.
     * @param discount The discount applied to the invoice.
     * @param payable  The payable amount of the invoice.
     * @param paid     The amount paid for the invoice.
     * @param returned The returned amount for the invoice.
     */
    public Invoice(String id, Employee employee, double total, double sgst, double cgst, double discount, double payable, double paid, double returned) {
        this.id = id;
        this.employee = employee;
        this.total = total;
        this.sgst = sgst;
        this.cgst = cgst;
        this.discount = discount;
        this.payable = payable;
        this.paid = paid;
        this.returned = returned;
    }

    // Getters and setters

    /**
     * Gets the ID of the invoice.
     *
     * @return The ID of the invoice.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the invoice.
     *
     * @param id The ID of the invoice.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the employee associated with the invoice.
     *
     * @return The employee associated with the invoice.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee associated with the invoice.
     *
     * @param employee The employee associated with the invoice.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Gets the total amount of the invoice.
     *
     * @return The total amount of the invoice.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total amount of the invoice.
     *
     * @param total The total amount of the invoice.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Gets the SGST amount of the invoice.
     *
     * @return The SGST amount of the invoice.
     */
    public double getSgst() {
        return sgst;
    }

    /**
     * Sets the SGST amount of the invoice.
     *
     * @param sgst The SGST amount of the invoice.
     */
    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    /**
     * Gets the CGST amount of the invoice.
     *
     * @return The CGST amount of the invoice.
     */
    public double getCgst() {
        return cgst;
    }

    /**
     * Sets the CGST amount of the invoice.
     *
     * @param cgst The CGST amount of the invoice.
     */
    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    /**
     * Gets the discount applied to the invoice.
     *
     * @return The discount applied to the invoice.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount applied to the invoice.
     *
     * @param discount The discount applied to the invoice.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets the payable amount of the invoice.
     *
     * @return The payable amount of the invoice.
     */
    public double getPayable() {
        return payable;
    }

    /**
     * Sets the payable amount of the invoice.
     *
     * @param payable The payable amount of the invoice.
     */
    public void setPayable(double payable) {
        this.payable = payable;
    }

    /**
     * Gets the amount paid for the invoice.
     *
     * @return The amount paid for the invoice.
     */
    public double getPaid() {
        return paid;
    }

    /**
     * Sets the amount paid for the invoice.
     *
     * @param paid The amount paid for the invoice.
     */
    public void setPaid(double paid) {
        this.paid = paid;
    }

    /**
     * Gets the returned amount for the invoice.
     *
     * @return The returned amount for the invoice.
     */
    public double getReturned() {
        return returned;
    }

    /**
     * Sets the returned amount for the invoice.
     *
     * @param returned The returned amount for the invoice.
     */
    public void setReturned(double returned) {
        this.returned = returned;
    }

    /**
     * Gets the date and time of the invoice.
     *
     * @return The date and time of the invoice.
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * Sets the date and time of the invoice.
     *
     * @param datetime The date and time of the invoice.
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", employee=" + employee +
                ", total=" + total +
                ", sgst=" + sgst +
                ", cgst=" + cgst +
                ", discount=" + discount +
                ", payable=" + payable +
                ", paid=" + paid +
                ", returned=" + returned +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
