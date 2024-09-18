package com.pos.point_of_sales.entity;


import java.io.Serializable;

public class ProductVariant implements Serializable {
    private long id;
    private long productId; // Foreign key to reference the product
    private String variantName;
    private double variantQuantity;

    public ProductVariant(long id, long productId, String variantName, double variantQuantity) {
        this.id = id;
        this.productId = productId;
        this.variantName = variantName;
        this.variantQuantity = variantQuantity;
    }

    public ProductVariant(long productId, String variantName, double variantQuantity) {
        this.productId = productId;
        this.variantName = variantName;
        this.variantQuantity = variantQuantity;
    }
    public ProductVariant()
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public double getVariantQuantity() {
        return variantQuantity;
    }

    public void setVariantQuantity(double variantQuantity) {
        this.variantQuantity = variantQuantity;
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
                "id=" + id +
                ", productId=" + productId +
                ", variantName='" + variantName + '\'' +
                ", variantQuantity=" + variantQuantity +
                '}';
    }
}