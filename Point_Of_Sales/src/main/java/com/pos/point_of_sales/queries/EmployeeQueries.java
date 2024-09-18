package com.pos.point_of_sales.queries;

/**
 * Contains SQL queries related to employees.
 * @author Mahesh_Yadav
 */
public class EmployeeQueries {

    /**
     * SQL query to select all employees.
     */
    public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM Employees";

    /**
     * SQL query to select an employee by their ID.
     */
    public static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM Employees WHERE id = ?";

    /**
     * SQL query to select an employee's type by their username.
     */
    public static final String SELECT_EMPLOYEE_BY_TYPE = "SELECT type FROM employees WHERE username = ?";

    /**
     * SQL query to save a new employee into the database.
     */
    public static final String SAVE_EMPLOYEE = "INSERT INTO Employees (id ,firstname, lastname, username, password, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL query to update an existing employee in the database.
     */
    public static final String UPDATE_EMPLOYEE = "UPDATE Employees SET firstname = ?, lastname = ?, username = ?, password = ?, phone = ?, address = ?  WHERE id = ?";

    /**
     * SQL query to delete an employee from the database.
     */
    public static final String DELETE_EMPLOYEE = "DELETE FROM Employees WHERE id = ?";

    /**
     * SQL query to check if a user exists.
     */
    public static final String CHECK_USER = "SELECT COUNT(*) AS count FROM employees WHERE username=?";

    /**
     * SQL query to check the password of an employee.
     */
    public static final String CHECK_PASSWORD = "SELECT password FROM Employees WHERE username =?";
}
