package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.CardPayDao;
import com.pos.point_of_sales.entity.CardPayment;
import com.pos.point_of_sales.populators.CardPayPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.CardPaymentQueries.*;

/**
 * CardPayModel class provides methods for interacting with card payments in the database.
 * The CardPayModel class  implements the CardPayDao interface to provide data access methods related to Card Payments.
 * It handles CRUD operations for Card Payments.
 *
 * @author Mahesh Yadav
 * @version 1.0
 */
public class CardPayModel implements CardPayDao {
    private static final Logger LOG = LoggerFactory.getLogger(CardPayModel.class);

    private CardPayPopulator cardPayPopulator = new CardPayPopulator();
    private CardPayment cardPayment;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves all card payments from the database.
     *
     * @return an ObservableList of CardPayment objects representing all card payments
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<CardPayment> getPayments() throws SQLException {
        ObservableList<CardPayment> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_PAYMENTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(cardPayPopulator.populatePayment(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching payments: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a card payment by its ID from the db.
     *
     * @param id the ID of the card payment
     * @return the CardPayment object
     * @throws SQLException
     */
    @Override
    public CardPayment getPayment_By_Id(String id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PAYMENTS_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cardPayment = cardPayPopulator.populatePayment(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching payment by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return cardPayment;
    }

    /**
     * Saves a new Card Payment to the db.
     *
     * @param cardPayment
     * @throws SQLException
     */
    @Override
    public void savePayments(CardPayment cardPayment) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_PAYMENTS);
            cardPayPopulator.savePayment(preparedStatement, cardPayment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving payments: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a card payment from the db.
     *
     * @param cardPayment
     * @throws SQLException
     */
    @Override
    public void deletePayments(CardPayment cardPayment) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_PAYMENTS);
            cardPayPopulator.deletePayment(preparedStatement, cardPayment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting payments: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing card payment in the db.
     *
     * @param cardPayment
     * @throws SQLException
     */
    @Override
    public void updatePayments(CardPayment cardPayment) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PAYMENTS);
            cardPayPopulator.paymentUpdate(preparedStatement, cardPayment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating payments: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
