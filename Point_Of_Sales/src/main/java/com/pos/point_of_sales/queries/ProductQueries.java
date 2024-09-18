package com.pos.point_of_sales.queries;

/**
 * Contains SQL queries related to products.
 *
 * @author Mahesh_Yadav
 */
public class ProductQueries {

    /**
     * SQL query to select all products from the database.
     */
    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Products";

    /**
     * SQL query to select a product by its name.
     */
    public static final String GET_PROD_BY_NAME =  "SELECT * FROM Products WHERE name = ?";

    /**
     * SQL query to select a product by its ID.
     */
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Products WHERE id = ?";

    /**
     * SQL query to select a product by its variant product grams.
     */
    public static final String SELECT_PRODUCT_BY_VARPROD = "SELECT * FROM Products WHERE variantProd_gms = ?";

    /**
     * SQL query to select a product by its barcode.
     */
    public static final String SELECT_PRODUCT_BY_BARCODE = "SELECT * FROM Products WHERE barcode = ?";

    /**
     * SQL query to save a product into the database.
     */
    public static final String SAVE_PRODUCT = "INSERT INTO Products (barcode, id, categoryId, supplierId, name, price, quantity,  description, entrydate,variantProd_gms) VALUES ( ?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

    /**
     * SQL query to update a product in the database.
     */
    public static final String UPDATE_PRODUCT = "UPDATE Products SET name = ?, categoryId = ?, supplierId =?,  price = ?, quantity = ?, description = ?, entrydate = ? WHERE id = ?";

    /**
     * SQL query to delete a product from the database.
     */
    public static final String DELETE_PRODUCT = "DELETE FROM Products WHERE id = ?";

    /**
     * SQL query to decrease the quantity of a product in the database.
     */
    public static final String DECREASE_PRODUCT = "UPDATE Products SET quantity = ? WHERE id = ?";

    /**
     * SQL query to increase the quantity of a product in the database.
     */
    public static final String INCREASE_PRODUCT = "UPDATE Products SET quantity =  ? WHERE id = ?";

    /**
     * SQL query to select all product names from the database.
     */
    public static final String SELECT_PRODUCT_NAMES = "SELECT name FROM Products";
}
