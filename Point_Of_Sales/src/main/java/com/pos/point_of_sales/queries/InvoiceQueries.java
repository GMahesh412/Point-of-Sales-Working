package com.pos.point_of_sales.queries;


/**
 * Contains SQL queries related to invoices.
 *
 * @author Mahesh_Yadav
 */
public class InvoiceQueries {

    /**
     * SQL query to select all invoices.
     */
    public static final String SELECT_ALL_INVOICES = "SELECT * FROM Invoices";

    /**
     * SQL query to select invoices by employee.
     */
    public static final String SELECT_INVOICE_BY_EMP = "SELECT * FROM Invoices";

    /**
     * SQL query to select an invoice by its ID.
     */
    public static final String SELECT_INVOICE_BY_ID = "SELECT * FROM Invoices WHERE id = ?";

    /**
     * SQL query to save an invoice into the database.
     */
    public static final String SAVE_INVOICE = "INSERT INTO Invoices (id,  employeeId,  total,  sgst,  discount,  payable,  paid,  returned,  datetime, cgst) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to delete an invoice from the database.
     */
    public static final String DELETE_INVOICE = "DELETE FROM Invoices WHERE id = ?";
}

