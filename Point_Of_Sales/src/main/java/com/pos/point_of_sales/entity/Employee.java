package com.pos.point_of_sales.entity;

import java.io.Serializable;

/**
 * Represents an employee in the Point of Sales system.
 *  * @author Mahesh_Yadav
 */
public class Employee implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phone;
    private String address;
    private String type;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
    }

    /**
     * Constructs an Employee with specified parameters.
     *
     * @param firstName The first name of the employee.
     * @param lastName  The last name of the employee.
     * @param userName  The username of the employee.
     * @param password  The password of the employee.
     * @param phone     The phone number of the employee.
     * @param address   The address of the employee.
     */
    public Employee(String firstName, String lastName, String userName, String password, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Constructs an Employee with specified parameters.
     *
     * @param id        The ID of the employee.
     * @param firstName The first name of the employee.
     * @param lastName  The last name of the employee.
     * @param userName  The username of the employee.
     * @param password  The password of the employee.
     * @param phone     The phone number of the employee.
     * @param address   The address of the employee.
     */
    public Employee(long id, String firstName, String lastName, String userName, String password, String phone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Constructs an Employee with specified parameters.
     *
     * @param id        The ID of the employee.
     * @param firstName The first name of the employee.
     * @param lastName  The last name of the employee.
     * @param userName  The username of the employee.
     * @param password  The password of the employee.
     * @param phone     The phone number of the employee.
     * @param address   The address of the employee.
     * @param type      The type of the employee.
     */
    public Employee(long id, String firstName, String lastName, String userName, String password, String phone, String address, String type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    /**
     * Gets the ID of the employee.
     *
     * @return The ID of the employee.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the employee.
     *
     * @param id The ID of the employee.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the first name of the employee.
     *
     * @return The first name of the employee.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the employee.
     *
     * @param firstName The first name of the employee.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the employee.
     *
     * @return The last name of the employee.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the employee.
     *
     * @param lastName The last name of the employee.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the username of the employee.
     *
     * @return The username of the employee.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the employee.
     *
     * @param userName The username of the employee.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password of the employee.
     *
     * @return The password of the employee.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the employee.
     *
     * @param password The password of the employee.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the phone number of the employee.
     *
     * @return The phone number of the employee.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the employee.
     *
     * @param phone The phone number of the employee.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the address of the employee.
     *
     * @return The address of the employee.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the employee.
     *
     * @param address The address of the employee.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the type of the employee.
     *
     * @return The type of the employee.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     *
     * @param type The type of the employee.
     */
    public void setType(String type) {
        this.type = type;
    }
}
