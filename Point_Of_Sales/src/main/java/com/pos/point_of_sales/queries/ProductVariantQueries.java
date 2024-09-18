package com.pos.point_of_sales.queries;

/**
 * Contains SQL queries related to product variants.
 *
 * @author Mahesh_Yadav
 */
public class ProductVariantQueries {

    /**
     * SQL query to select all product variants from the database.
     */
    public static final String SELECT_ALL_VARIANTS = "SELECT * FROM product_variants";

    /**
     * SQL query to select product variants by price per kilogram.
     */
    public static final String SELECT_PRICE_BY_PRICEPERKG = "SELECT * FROM product_variants WHERE price_per_kg = ?";

    /**
     * SQL query to insert a new product variant into the database.
     */
    public static final String INSERT_VARIANT = "INSERT INTO product_variants (id, product_id, variant_name, variant_quantity) VALUES (?, ?, ?, ?)";

    /**
     * SQL query to update a product variant in the database.
     */
    public static final String UPDATE_VARIANT = "UPDATE product_variants SET product_id = ?, price_per_kg = ?, qty_per_kg = ? WHERE id = ?";

    /**
     * SQL query to delete a product variant from the database.
     */
    public static final String DELETE_VARIANT = "DELETE FROM product_variants WHERE id = ?";
}
