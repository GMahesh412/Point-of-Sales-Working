package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.InvoiceDao;
import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.populators.InvoicePopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.InvoiceQueries.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  InvoiceModel class implements the InvoiceDao interface to provide data access methods related to invoices.
 * It handles db operations for managing invoice records.
 *
 * @author Mahesh Yadav
 */
public class InvoiceModel implements InvoiceDao {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceModel.class);

    private EmployeeModel employeeModel;
    private InvoiceModel invoiceModel;
    private InvoicePopulator invoicePopulator = new InvoicePopulator();
    private Invoice invoice;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves all invoices from the db.
     *
     * @return an ObservableList of Invoice objects representing all invoices
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Invoice> getInvoices() throws SQLException {
        ObservableList<Invoice> list = FXCollections.observableArrayList();
        try {
            invoice = new Invoice();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_INVOICES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(invoicePopulator.populateInvoice(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving all invoices: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves all invoices associated with a specific employee from the database.
     *
     * @param employeeModel the EmployeeModel object representing the employee
     * @return an ObservableList of Invoice objs.
     * @throws SQLException
     */
    @Override
    public ObservableList<Invoice> getInvoicesbyEmp(EmployeeModel employeeModel) throws SQLException {
        ObservableList<Invoice> list = FXCollections.observableArrayList();
        try {
            invoice = new Invoice();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_INVOICES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(invoicePopulator.populateInvoice(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving invoices by employee: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves an invoice by its ID from the db.
     *
     * @param id the ID of the invoice to retrieve
     * @return the Invoice Obj.
     * @throws SQLException
     */
    @Override
    public Invoice getInvoice(String id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_INVOICE_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invoice = invoicePopulator.populateInvoice(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving invoice by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return invoice;
    }

    /**
     * Saves an invoice record to the Db.
     * @param invoice the Invoice object to Save
     * @throws SQLException
     */
    @Override
    public void saveInvoice(Invoice invoice) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_INVOICE);
            invoicePopulator.populatePreparedStatement(preparedStatement, invoice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving invoice: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes an invoice record from the db.
     *
     * @param invoice the Invoice object to Delete
     * @throws SQLException
     */
    @Override
    public void deleteInvoice(Invoice invoice) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_INVOICE);
            invoicePopulator.populatePreparedStatementForDelete(preparedStatement, invoice);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting invoice: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
