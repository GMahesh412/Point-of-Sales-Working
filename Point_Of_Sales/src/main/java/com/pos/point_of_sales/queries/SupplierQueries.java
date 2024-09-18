package com.pos.point_of_sales.queries;

import com.pos.point_of_sales.entity.Supplier;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Contains SQL queries related to suppliers.
 *
 * @author Mahesh_Yadav
 */
public class SupplierQueries {

    /**
     * SQL query to select all suppliers from the database.
     */
    public static final String SELECT_ALL_SUPPLIERS = "SELECT * FROM Suppliers";

    /**
     * SQL query to select a supplier by its ID from the database.
     */
    public static final String SELECT_SUPPLIER =  "SELECT * FROM Suppliers WHERE id = ?";

    /**
     * SQL query to select names of all suppliers from the database.
     */
    public static final String SELECT_SUPPLIER_NAMES = "SELECT name FROM Suppliers";

    /**
     * SQL query to insert a new supplier into the database.
     */
    public static final String SAVE_SUPPLIER = "INSERT INTO Suppliers (id, name, phone, address, createddate, state) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to update a supplier in the database.
     */
    public static final String UPDATE_SUPPLIER = "UPDATE Suppliers SET name = ?, phone = ?, address = ?, state = ?, createddate = ? WHERE id = ?";

    /**
     * SQL query to delete a supplier from the database.
     */
    public static final String DELETE_SUPPLIER = "DELETE FROM Suppliers WHERE id = ?";
}
