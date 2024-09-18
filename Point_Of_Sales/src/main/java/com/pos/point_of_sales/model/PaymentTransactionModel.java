package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.PaymentTransactionDao;
import com.pos.point_of_sales.entity.PaymentTransaction;
import com.pos.point_of_sales.populators.PaymentTransactionPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.PaymentTransactionQueries.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PaymentTransactionModel class implements the PaymentTransactionDao interface to provide data access methods
 * related to payment transactions. It handles DB operations for managing payment transaction records.
 *
 * @author Mahesh Yadav
 */
public class PaymentTransactionModel implements PaymentTransactionDao {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentTransactionModel.class);

    private EmployeeModel employeeModel;
    private PaymentTransaction paymentTransaction;
    private Connection connection = null;
    private PaymentTransactionPopulator paymentTransactionPopulator = new PaymentTransactionPopulator();
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves all payment transactions from the db.
     *
     * @return an ObservableList of PaymentTransaction objects representing all payment transactions
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<PaymentTransaction> getTransactions() throws SQLException {
        ObservableList<PaymentTransaction> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(GET_PAYMENTS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(paymentTransactionPopulator.populatePayment(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving payment transactions: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a payment transaction by its ID from the db.
     *
     * @param id the ID of the payment transaction to retrieve
     * @return  PaymentTransaction object representing the payment transaction with the given ID
     * @throws SQLException
     */
    @Override
    public PaymentTransaction getTransaction_By_Id(String id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(GET_PAYMENT_QUERY);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                paymentTransaction = paymentTransactionPopulator.populatePayment(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving payment transaction by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return paymentTransaction;
    }

    /**
     * Saves a payment transaction record to the database.
     *
     * @param paymentTransaction the PaymentTransaction object to Save
     * @throws SQLException
     */
    @Override
    public void saveTransaction(PaymentTransaction paymentTransaction) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_QUERY);
            paymentTransactionPopulator.savePayment(preparedStatement, paymentTransaction);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving payment transaction: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a payment transaction record from the db.
     *
     * @param paymentTransaction the PaymentTransaction object to Delete
     * @throws SQLException
     */
    @Override
    public void deleteTransaction(PaymentTransaction paymentTransaction) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            paymentTransactionPopulator.deletePayment(preparedStatement, paymentTransaction);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting payment transaction: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates a payment transaction record in the db.
     *
     * @param paymentTransaction the PaymentTransaction object to update
     * @throws SQLException
     */
    @Override
    public void updateTransaction(PaymentTransaction paymentTransaction) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            paymentTransactionPopulator.paymentUpdate(preparedStatement, paymentTransaction);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating payment transaction: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Retrieves a payment transaction by its order number from the db.
     *
     * @param orderNumber the order number of the payment transaction to retrieve
     * @return the PaymentTransaction object representing the payment transaction with the given order number
     * @throws SQLException
     */
    @Override
    public PaymentTransaction getOrderNumber(String orderNumber) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(GET_ORDER_ID);
            preparedStatement.setString(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                paymentTransaction = paymentTransactionPopulator.populatePayment(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving payment transaction by order number: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return paymentTransaction;
    }
}
