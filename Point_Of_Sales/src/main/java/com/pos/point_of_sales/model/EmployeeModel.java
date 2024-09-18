package com.pos.point_of_sales.model;


import com.pos.point_of_sales.dao.EmployeeDao;
import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Employee;
import com.pos.point_of_sales.populators.EmployeePopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.EmployeeQueries.*;

/**
 * The EmployeeModel class implements the EmployeeDao interface to provide data access methods related to employees.
 * It handles DB,CRUD operations for managing employee records.
 * @author Mahesh Yadav
 */
public class EmployeeModel implements EmployeeDao {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeModel.class);

    private Employee employee;
    private EmployeePopulator employeePopulator = new EmployeePopulator();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves a list of all employees from the Db.
     *
     * @return ObservableList of Employee objects representing all employees.
     * @throws SQLException if a db access error occurs
     */
    @Override
    public ObservableList<Employee> getEmployees() throws SQLException {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(employeePopulator.populateEmployee(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving employees: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
        return list;
    }
    /**
     * Retrieves an employee by ID from the database.
     *
     * @param id the ID of the employee to retrieve
     * @return the Employee object corresponding to the given ID, or null if not found
     * @throws SQLException
     */
    @Override
    public Employee getEmployee(long id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = employeePopulator.populateEmployee(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving employee by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return employee;
    }

    /**
     * Retrieves the type of emp based on the username from the DB.
     *
     * @param username the username of the emp.
     * @return the type of employee
     * @throws SQLException
     */
    @Override
    public String getEmployeeType(String username) throws SQLException {
        String type = null;
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_TYPE);
            employeePopulator.checkUserType(preparedStatement, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                type = resultSet.getString("type");
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving employee type: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return type;
    }

    /**
     * Saves an employee record to the database.
     *
     * @param employee the Employee object to Save
     * @throws SQLException
     */
    @Override
    public void saveEmployee(Employee employee) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_EMPLOYEE);
            employeePopulator.saveEmployee(preparedStatement, employee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving employee: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an emp record in the db.
     *
     * @param employee the Employee object to Update
     * @throws SQLException
     */
    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
            employeePopulator.updateEmp(preparedStatement, employee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating employee: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes an emp record from the database.
     *
     * @param employee the Employee object to Delete
     * @throws SQLException
     */
    @Override
    public void deleteEmployee(Employee employee) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);
            employeePopulator.deleteEmp(preparedStatement, employee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting employee: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Checks if a given username exists in the DB.
     *
     * @param username the username to check
     * @return true if the username exists, Otherwise false
     * @throws SQLException
     */
    @Override
    public boolean checkUser(String username) throws SQLException {
        boolean exists = false;
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER);
            employeePopulator.checkUserType(preparedStatement, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while checking user: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
        return exists;
    }

    /**
     * it checks if  provided password matches the stored password for the given username.
     *
     * @param username the username to check
     * @param password the password to compare
     * @return true if the password matches, otherwise false .
     * @throws SQLException
     */
    @Override
    public boolean checkPassword(String username, String password) throws SQLException {
        boolean match = false;
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_PASSWORD);
            employeePopulator.checkUserType(preparedStatement, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                match = storedPassword.equals(password);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while checking password: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return match;
    }
}















/*
 package com.pos.point_of_sales.model;

import com.pos.point_of_sales.dao.EmployeeDao;
import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Employee;
import com.pos.point_of_sales.populators.CategoryPopulator;
import com.pos.point_of_sales.populators.EmployeePopulator;

import com.pos.point_of_sales.queries.EmployeeQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.CategoryQueries.SELECT_ALL_CATEGORIES;
import static com.pos.point_of_sales.queries.EmployeeQueries.*;

 public class EmployeeModel implements EmployeeDao {
     private EmployeePopulator employeePopulator;
     private static PreparedStatement preparedStatement = null;

    @Override
    public ObservableList<Employee> getEmployees() throws SQLException {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        Connection connection = null;
        employeePopulator = new EmployeePopulator();
        try
        {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(employeePopulator.populateEmployee(resultSet));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            connection.close();
        }
        return list;
    }

    @Override
    public Employee getEmployee(long id) throws SQLException {
        Employee employee = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        employeePopulator = new EmployeePopulator();
        try
        {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                employee=employeePopulator.populateEmployee(resultSet);
            }

          */
/*  preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                employee=employeePopulator.populateEmployee(resultSet);
            }
            *//*

        }catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            connection.close();
        }
        return employee;
    }

     @Override
     public String getEmployeeType(String username) throws SQLException {
         String type = null;
         Connection connection = null;
         PreparedStatement preparedStatement = null;
         employeePopulator = new EmployeePopulator();
         try
         {
             connection = JDBCConnectionFactory.getConnection();
             preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_TYPE);
          employeePopulator.populateForCheckUser(preparedStatement,username);
            // preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery();
                 if (resultSet.next())
                 {
                     type = resultSet.getString("type");
                 }


        */
/*    preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    type = resultSet.getString("type");
                }
            }*//*


        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            connection.close();
        }
        return type;
    }

     @Override
     public void saveEmployee(Employee employee) throws SQLException {
         Connection connection = null;
         PreparedStatement preparedStatement = null;
         employeePopulator = new EmployeePopulator();
         try
         {
             connection = JDBCConnectionFactory.getConnection();
             preparedStatement = connection.prepareStatement(SAVE_EMPLOYEE);
             employeePopulator.populatePreparedStatementForSave(preparedStatement, employee);
             preparedStatement.executeUpdate();
         }
         catch (SQLException e)
         {
             handleSQLException(e);
         }finally {
             connection.close();
         }
     }

     @Override
     public void updateEmployee(Employee employee) throws SQLException {
         Connection connection = null;
         employeePopulator= new EmployeePopulator();
         PreparedStatement preparedStatement = null;
         try
         {
             connection = JDBCConnectionFactory.getConnection();
             preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);
             employeePopulator.empPopulatePreparedStatement(preparedStatement,employee);
             preparedStatement.executeUpdate();
         } catch (SQLException e)
         {
             e.printStackTrace();
         }finally
         {
             connection.close();
         }
     }

     @Override
     public void deleteEmployee(Employee employee) throws SQLException {
         Connection connection = null;
         employeePopulator= new EmployeePopulator();
         PreparedStatement preparedStatement = null;
         try
         {
             connection = JDBCConnectionFactory.getConnection();
           */
/*  preparedStatement = connection.prepareStatement(EmployeeHelper.DELETE_EMPLOYEE);
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.executeUpdate();*//*

             preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE);
             employeePopulator.populatePreparedStatementForDelete(preparedStatement,employee);
             preparedStatement.executeUpdate();


        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            connection.close();
        }
    }
    @Override
    public boolean checkUser(String username) throws SQLException {
        boolean exists = false;
        Connection connection = null;
        try {
            connection = JDBCConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM employees WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                exists = resultSet.getInt("count") > 0;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            connection.close();
        }
        return exists;
    }

    @Override
    public boolean checkPassword(String username, String password) throws SQLException {
        boolean match = false;
        Connection connection = null;
        try {
            connection = JDBCConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM employees WHERE username =?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String storedPassword = resultSet.getString("password");
                match = storedPassword.equals(password);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }finally
        {
            connection.close();
        }
        return match;
    }
}

*/
