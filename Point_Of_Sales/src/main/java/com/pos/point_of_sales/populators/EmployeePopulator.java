package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.Employee;
import com.pos.point_of_sales.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeePopulator {

    public Employee populateEmployee(ResultSet resultSet) throws SQLException
    {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("firstname"));
        employee.setLastName(resultSet.getString("lastname"));
        employee.setUserName(resultSet.getString("username"));
        employee.setPassword(resultSet.getString("password"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setAddress(resultSet.getString("address"));
        employee.setType(resultSet.getString("type").toString());
        return employee;
    }
    public  PreparedStatement saveEmployee(PreparedStatement preparedStatement, Employee employee) throws SQLException {
        preparedStatement.setLong(1, employee.getId());
        preparedStatement.setString(2, employee.getFirstName());
        preparedStatement.setString(3, employee.getLastName());
        preparedStatement.setString(4, employee.getUserName());
        preparedStatement.setString(5, employee.getPassword());
        preparedStatement.setString(6, employee.getPhone());
        preparedStatement.setString(7, employee.getAddress());
        //preparedStatement.setString(8,employee.getType().toString());
        return preparedStatement;
    }

    public  void deleteEmp(PreparedStatement preparedStatement,  Employee employee) throws SQLException {
        preparedStatement.setLong(1, employee.getId());
    }
    public  void checkUserType(PreparedStatement preparedStatement, String username) throws SQLException {
        preparedStatement.setString(1, username);

    }

    public  PreparedStatement updateEmp(PreparedStatement preparedStatement, Employee employee) throws SQLException {

        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getUserName());
        preparedStatement.setString(4, employee.getPassword());
        preparedStatement.setString(5, employee.getPhone());
        preparedStatement.setString(6, employee.getAddress());
     //   preparedStatement.setString(7,employee.getType());
        preparedStatement.setLong(7, employee.getId());
        return preparedStatement;
    }


}
