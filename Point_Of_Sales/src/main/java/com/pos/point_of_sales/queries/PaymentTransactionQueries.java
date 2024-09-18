package com.pos.point_of_sales.queries;

/**
 * Contains SQL queries related to payment transactions.
 *
 * @author Mahesh_Yadav
 */
public class PaymentTransactionQueries {

    /**
     * SQL query to save a payment transaction into the database.
     */
    public static final String SAVE_QUERY = "INSERT INTO PaymentTransactions (transactionId,  cardNumber,  cvv,  expiryDate,  totalAmount, merchantName,  orderNumber,  paymentStatus) VALUES (?, ?, ?, ?, ?,?, ?, ?)";

    /**
     * SQL query to delete a payment transaction from the database.
     */
    public static final String DELETE_QUERY = "DELETE FROM PaymentTransactions WHERE transactionId = ?";

    /**
     * SQL query to update a payment transaction in the database.
     */
    public static final String UPDATE_QUERY = "UPDATE PaymentTransactions SET ... WHERE transactionId = ?";

    /**
     * SQL query to retrieve a payment transaction by its ID.
     */
    public static final String GET_PAYMENT_QUERY = "SELECT * FROM PaymentTransactions WHERE transactionId = ?";

    /**
     * SQL query to retrieve the order ID associated with a payment transaction.
     */
    public static final String GET_ORDER_ID = "SELECT * FROM PaymentTransactions WHERE orderNumber = ?";

    /**
     * SQL query to retrieve all payment transactions from the database.
     */
    public static final String GET_PAYMENTS_QUERY = "SELECT * FROM PaymentTransactions";

}
