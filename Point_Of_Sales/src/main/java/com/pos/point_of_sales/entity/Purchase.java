package com.pos.point_of_sales.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a purchase made in the point of sales system.
 * @author Mahesh Yadav
 */
public class Purchase implements Serializable {

    private long id;

    private Product product;

    private Supplier supplier;

    private double quantity;

    private double price;

    private double total;

    private Date datetime;

    /**
     * Default constructor for Purchase class.
     */
    public Purchase() {
    }

    /**
     * Constructor for creating a Purchase object with all details.
     *
     * @param id        The ID of the purchase.
     * @param product   The product purchased.
     * @param supplier  The supplier from whom the product was purchased.
     * @param quantity  The quantity of the product purchased.
     * @param price     The price per unit of the product purchased.
     * @param total     The total cost of the purchase.
     * @param datetime  The date and time when the purchase was made.
     */
    public Purchase(long id, Product product, Supplier supplier, double quantity, double price, double total, Date datetime) {
        this.id = id;
        this.product = product;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.datetime = datetime;
    }

    /**
     * Constructor for creating a Purchase object with necessary details.
     *
     * @param product   The product purchased.
     * @param supplier  The supplier from whom the product was purchased.
     * @param quantity  The quantity of the product purchased.
     * @param price     The price per unit of the product purchased.
     * @param total     The total cost of the purchase.
     * @param datetime  The date and time when the purchase was made.
     */
    public Purchase(Product product, Supplier supplier, double quantity, double price, double total, Date datetime) {
        this.product = product;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.datetime = datetime;
    }

    /**
     * Retrieves the ID of the purchase.
     *
     * @return The ID of the purchase.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the purchase.
     *
     * @param id The ID of the purchase to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the product purchased.
     *
     * @return The product purchased.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product purchased.
     *
     * @param product The product purchased to set.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Retrieves the supplier from whom the product was purchased.
     *
     * @return The supplier from whom the product was purchased.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Sets the supplier from whom the product was purchased.
     *
     * @param supplier The supplier from whom the product was purchased to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Retrieves the quantity of the product purchased.
     *
     * @return The quantity of the product purchased.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product purchased.
     *
     * @param quantity The quantity of the product purchased to set.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the price per unit of the product purchased.
     *
     * @return The price per unit of the product purchased.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per unit of the product purchased.
     *
     * @param price The price per unit of the product purchased to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the total cost of the purchase.
     *
     * @return The total cost of the purchase.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of the purchase.
     *
     * @param total The total cost of the purchase to set.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Retrieves the date and time when the purchase was made.
     *
     * @return The date and time when the purchase was made.
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * Sets the date and time when the purchase was made.
     *
     * @param datetime The date and time when the purchase was made to set.
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", product=" + product +
                ", supplier=" + supplier +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                ", datetime=" + datetime +
                '}';
    }
}
