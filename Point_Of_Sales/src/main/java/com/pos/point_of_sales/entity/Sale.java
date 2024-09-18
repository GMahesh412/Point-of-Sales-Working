package com.pos.point_of_sales.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents a sale made in the point of sales system.
 * @author Mahesh Yadav
 */
public class Sale implements Serializable {

    /** The unique ID of the sale. */
    private long id;
    private Invoice invoice;
    private Product product;
    private double quantity;
    private double price;
    private double total;

    /** The date and time when the sale was made. */
    private Date datetime;

    /**
     * Default constructor for Sale class.
     */
    public Sale() {
    }

    /**
     * Constructor for creating a Sale object with all details.
     *
     * @param id        The ID of the sale.
     * @param invoice   The invoice associated with the sale.
     * @param product   The product sold.
     * @param quantity  The quantity of the product sold.
     * @param price     The price per unit of the product sold.
     * @param total     The total cost of the sale.
     * @param datetime  The date and time when the sale was made.
     */
    public Sale(long id, Invoice invoice, Product product, double quantity, double price, double total, Date datetime) {
        this.id = id;
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.datetime = datetime;
    }

    /**
     * Constructor for creating a Sale object with necessary details.
     *
     * @param invoice   The invoice associated with the sale.
     * @param product   The product sold.
     * @param quantity  The quantity of the product sold.
     * @param price     The price per unit of the product sold.
     * @param total     The total cost of the sale.
     */
    public Sale(Invoice invoice, Product product, double quantity, double price, double total) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    /**
     * Constructor for creating a Sale object with all details including datetime.
     *
     * @param invoice   The invoice associated with the sale.
     * @param product   The product sold.
     * @param quantity  The quantity of the product sold.
     * @param price     The price per unit of the product sold.
     * @param total     The total cost of the sale.
     * @param datetime  The date and time when the sale was made.
     */
    public Sale(Invoice invoice, Product product, double quantity, double price, double total, Date datetime) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.datetime = datetime;
    }

    /** Retrieves the ID of the sale. */
    public long getId() {
        return id;
    }

    /** Sets the ID of the sale. */
    public void setId(long id) {
        this.id = id;
    }

    /** Retrieves the invoice associated with the sale. */
    public Invoice getInvoice() {
        return invoice;
    }

    /** Sets the invoice associated with the sale. */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /** Retrieves the product sold. */
    public Product getProduct() {
        return product;
    }

    /** Sets the product sold. */
    public void setProduct(Product product) {
        this.product = product;
    }

    /** Retrieves the quantity of the product sold. */
    public double getQuantity() {
        return quantity;
    }

    /** Sets the quantity of the product sold. */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /** Retrieves the price per unit of the product sold. */
    public double getPrice() {
        return price;
    }

    /** Sets the price per unit of the product sold. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Retrieves the total cost of the sale. */
    public double getTotal() {
        return total;
    }

    /** Sets the total cost of the sale. */
    public void setTotal(double total) {
        this.total = total;
    }

    /** Retrieves the date and time when the sale was made. */
    public java.sql.Date getDatetime() {
        return datetime;
    }

    /** Sets the date and time when the sale was made. */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", invoice=" + invoice +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                ", datetime=" + datetime +
                '}';
    }
}
