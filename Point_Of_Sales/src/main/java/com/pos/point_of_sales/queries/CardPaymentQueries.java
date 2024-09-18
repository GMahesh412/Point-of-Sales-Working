package com.pos.point_of_sales.queries;

/**
 * Contains SQL queries related to card payments.
 *
 * @author Mahesh_Yadav
 */
public class CardPaymentQueries {

        /**
         * SQL query to select all card payments from the database.
         */
        public static final String SELECT_ALL_PAYMENTS ="SELECT * FROM CardPayments";

        /**
         * SQL query to select a card payment by its ID from the database.
         */
        public static final String SELECT_PAYMENTS_BY_ID= "SELECT * FROM CardPayments WHERE id = ?";

        /**
         * SQL query to insert a new card payment into the database.
         */
        public static final String SAVE_PAYMENTS= "INSERT INTO CardPayments (id, cardNumber, expiryDate, cvv, amount) VALUES (?, ?, ?, ?, ?)";

        /**
         * SQL query to delete a card payment from the database.
         */
        public static final String DELETE_PAYMENTS ="DELETE FROM CardPayments WHERE id = ?";

        /**
         * SQL query to update a card payment in the database.
         */
        public static final String UPDATE_PAYMENTS= "UPDATE CardPayments SET id = ?, cardNumber = ?, expiryDate = ?, cvv = ?, amount =? ";
}
