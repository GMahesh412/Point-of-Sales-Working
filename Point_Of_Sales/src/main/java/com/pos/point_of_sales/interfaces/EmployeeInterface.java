package com.pos.point_of_sales.interfaces;


import com.pos.point_of_sales.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents an interface for managing employees in the point of sales system.
 * @author Mahesh Yadav
 */
public interface EmployeeInterface {
    /**
     * The list of employees.
     */
    public ObservableList<Employee> EMPLOYEELIST = FXCollections.observableArrayList();
}
