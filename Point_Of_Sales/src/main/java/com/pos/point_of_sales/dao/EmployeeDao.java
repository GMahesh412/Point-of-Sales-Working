package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Employee;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing employees in the point of sales system.
 */
public interface EmployeeDao {

    /**
     * Retrieves a list of all employees.
     * @return An ObservableList of Employee objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Employee> getEmployees() throws SQLException;

    /**
     * Retrieves an employee by their ID.
     * @param id The ID of the employee to retrieve.
     * @return The Employee object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Employee getEmployee(long id) throws SQLException;

    /**
     * Retrieves the type of an employee based on their username.
     * @param username The username of the employee.
     * @return A string representing the type of the employee.
     * @throws SQLException if there is an error accessing the database.
     */
    public String getEmployeeType(String username) throws SQLException;

    /**
     * Saves a new employee to the database.
     * @param employee The Employee object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveEmployee(Employee employee) throws SQLException;

    /**
     * Updates an existing employee in the database.
     * @param employee The Employee object to be updated.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updateEmployee(Employee employee) throws SQLException;

    /**
     * Deletes an employee from the database.
     * @param employee The Employee object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteEmployee(Employee employee) throws SQLException;

    /**
     * Checks if the provided password matches the stored password for the given username.
     * @param username The username of the employee.
     * @param password The password to check.
     * @return true if the password matches, false otherwise.
     * @throws SQLException if there is an error accessing the database.
     */
    public boolean checkPassword(String username, String password) throws SQLException;

    /**
     * Checks if a user with the given username exists in the database.
     * @param username The username to check.
     * @return true if the user exists, false otherwise.
     * @throws SQLException if there is an error accessing the database.
     */
    public boolean checkUser(String username) throws SQLException;
}
