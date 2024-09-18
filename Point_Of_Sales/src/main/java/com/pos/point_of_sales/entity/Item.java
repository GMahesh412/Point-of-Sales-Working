package com.pos.point_of_sales.entity;

/**
 * Represents an item in the Point of Sales system.
 * @author Mahesh Yadav
 */
public class Item {

    private String itemName;
    private double unitPrice;
    private double quantity;
    private double total;
    private double sgst;
    private double cgst;
    private double discount;

    /**
     * Default constructor for Item.
     */
    public Item() {
    }

    /**
     * Constructs an Item with specified parameters.
     *
     * @param itemName  The name of the item.
     * @param unitPrice The unit price of the item.
     * @param quantity  The quantity of the item.
     * @param sgst      The SGST (State Goods and Services Tax) applied to the item.
     * @param cgst      The CGST (Central Goods and Services Tax) applied to the item.
     * @param discount  The discount applied to the item.
     * @param total     The total price of the item.
     */
    public Item(String itemName, double unitPrice, double quantity, double sgst, double cgst, double discount, double total) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
        this.sgst = sgst;
        this.cgst = cgst;
        this.discount = discount;
    }

    /**
     * Constructs an Item with specified parameters.
     *
     * @param unitPrice The unit price of the item.
     * @param quantity  The quantity of the item.
     * @param total     The total price of the item.
     */
    public Item(double unitPrice, double quantity, double total) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName The name of the item.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the unit price of the item.
     *
     * @return The unit price of the item.
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the item.
     *
     * @param unitPrice The unit price of the item.
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the quantity of the item.
     *
     * @return The quantity of the item.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity The quantity of the item.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the total price of the item.
     *
     * @return The total price of the item.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total price of the item.
     *
     * @param total The total price of the item.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Gets the SGST (State Goods and Services Tax) applied to the item.
     *
     * @return The SGST applied to the item.
     */
    public double getSgst() {
        return sgst;
    }

    /**
     * Sets the SGST (State Goods and Services Tax) applied to the item.
     *
     * @param sgst The SGST applied to the item.
     */
    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    /**
     * Gets the CGST (Central Goods and Services Tax) applied to the item.
     *
     * @return The CGST applied to the item.
     */
    public double getCgst() {
        return cgst;
    }

    /**
     * Sets the CGST (Central Goods and Services Tax) applied to the item.
     *
     * @param cgst The CGST applied to the item.
     */
    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    /**
     * Gets the discount applied to the item.
     *
     * @return The discount applied to the item.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount applied to the item.
     *
     * @param discount The discount applied to the item.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", total=" + total +
                ", sgst=" + sgst +
                ", cgst=" + cgst +
                ", discount=" + discount +
                '}';
    }
}
