package com.pos.point_of_sales.queries;

import com.pos.point_of_sales.entity.Sale;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Contains SQL queries related to sales.
 *
 * @author Mahesh_Yadav
 */
public class SalesQueries {

    /**
     * SQL query to select all sales from the database.
     */
    public static final String SELECT_ALL_SALES = "SELECT * FROM Sales";

    /**
     * SQL query to select a sale by its ID from the database.
     */
    public static final String SELECT_SALE = "SELECT * FROM Sales WHERE id = ?";

    /**
     * SQL query to select sales by product ID from the database.
     */
    public static final String SELECT_SALE_BY_PRODUCT_ID = "SELECT * FROM Sales WHERE productId = ?";

    /**
     * SQL query to insert a new sale into the database.
     */
    public static final String SAVE_SALE = "INSERT INTO Sales (invoiceId, productId, quantity, price, total, datetime) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to update a sale in the database.
     */
    public static final String UPDATE_SALE = "UPDATE Sales SET invoiceId=?, productId=?, quantity=?, price=?, total=?, datetime=? WHERE id = ?";

    /**
     * SQL query to delete a sale from the database.
     */
    public static final String DELETE_SALE = "DELETE FROM Sales WHERE id = ?";
}
