package com.pos.point_of_sales.entity;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * Represents a product in the point of sales system.
 * This class provides attributes and methods to manage product information.
 * It implements the Serializable interface to support serialization.
 * @author Mahesh_Yadav
 * @version 1.0
 * @since 2024-04-02
 */
public class Product implements Serializable {

    private String barcode;
    private long id;
    private Category category;
    private Supplier supplier;
    private String productName;
    private double price;
    private double quantity;
    private String description;
    private Date entrydate;
    private boolean variantProd;

    /**
     * Default constructor for Product class.
     */
    public Product() {

    }
    /**
     * Constructor for creating a Product object with basic details.
     * @param id The ID of the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     * @param quantity The quantity of the product.
     */
    public Product(long id, String productName, double price, double quantity)
    {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
    /**
     * Constructor for creating a Product object with full details.
     * @param id The ID of the product.
     * @param category The category of the product.
     * @param supplier The supplier of the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     * @param quantity The quantity of the product.
     * @param description The description of the product.
     * @param entrydate The entry date of the product.
     */
    public Product(long id, Category category, Supplier supplier, String productName, double price, double quantity, String description, Date entrydate) {
        this.id = id;
        this.category = category;
        this.supplier = supplier;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.entrydate = entrydate;
    }
    /**
     * Constructor for creating a Product object with full details.
     * @param category The category of the product.
     * @param supplier The supplier of the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     * @param quantity The quantity of the product.
     * @param description The description of the product.
     * @param entrydate The entry date of the product.
     */
    public Product(String productName, double price,
                   double quantity, String description, Category category, Supplier supplier, Date entrydate)
    {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.entrydate = entrydate;
    }

    /**
     * Constructor for creating a Product object with full details.
     * @param id The ID of the product.
     * @param category The category of the product.
     * @param supplier The supplier of the product.
     * @param productName The name of the product.
     * @param price The price of the product.
     * @param quantity The quantity of the product.
     * @param description The description of the product.
     * @param entrydate The entry date of the product.
     */
    public Product(String barcode, long id, Category category, Supplier supplier, String productName, double price, double quantity, String description, Date entrydate) {
        this.barcode = barcode;
        this.id = id;
        this.category = category;
        this.supplier = supplier;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.entrydate = entrydate;
    }

    public boolean getVariantProd() {
        return variantProd;
    }

    public void setVariantProd(boolean variantProd) {
        this.variantProd = variantProd;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEntrydate() {
        return entrydate;
    }
    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    /**
     * Returns a string representation of the Product object.
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Product{" +
                "barcode='" + barcode + '\'' +
                ", id=" + id +
                ", category=" + category +
                ", supplier=" + supplier +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", entrydate=" + entrydate +
                ", variantProd=" + variantProd +
                '}';
    }
}
