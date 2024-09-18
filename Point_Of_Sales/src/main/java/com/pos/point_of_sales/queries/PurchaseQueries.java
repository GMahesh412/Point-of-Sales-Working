package com.pos.point_of_sales.queries;

import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Supplier;

import java.sql.Date;

/**
 * Contains SQL queries related to purchases.
 *
 * @author Mahesh_Yadav
 */
public class PurchaseQueries {

    /**
     * SQL query to select all purchases from the database.
     */
    public static final String SELECT_ALL_PURCHASES = "SELECT * FROM Purchases";

    /**
     * SQL query to select a purchase by its ID from the database.
     */
    public static final String SELECT_PURCHASE_BY_ID = "SELECT * FROM Purchases WHERE id = ?";

    /**
     * SQL query to insert a new purchase into the database.
     */
    public static final String INSERT_PURCHASE = "INSERT INTO Purchases (id, productId, supplierId, quantity, price, total, datetime) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to update a purchase in the database.
     */
    public static final String UPDATE_PURCHASE = "UPDATE Purchases SET productId = ?, supplierId = ?, quantity = ?, datetime = ? WHERE id = ?";

    /**
     * SQL query to delete a purchase from the database.
     */
    public static final String DELETE_PURCHASE = "DELETE FROM Purchases WHERE id = ?";
}
