package com.pos.point_of_sales.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * Represents a supplier entity in the point of sales system.
 * @author Mahesh Yadav
 */
public class Supplier implements Serializable {


    private long id;
    private String name;
    private String phone;
    private String address;
    private String state;
    private Date createddate;

    /** Default constructor for Supplier class. */
    public Supplier() {
    }

    /**
     * Constructor for creating a Supplier object with basic details.
     *
     * @param name         The name of the supplier.
     * @param phone        The phone number of the supplier.
     * @param state        The state of the supplier.
     * @param address      The address of the supplier.
     * @param createddate  The date when the supplier was created.
     */
    public Supplier(String name, String phone, String state, String address, Date createddate) {
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.address = address;
        this.createddate = createddate;
    }

    /**
     * Constructor for creating a Supplier object with all details.
     *
     * @param id           The ID of the supplier.
     * @param name         The name of the supplier.
     * @param phone        The phone number of the supplier.
     * @param state        The state of the supplier.
     * @param address      The address of the supplier.
     * @param createddate  The date when the supplier was created.
     */
    public Supplier(long id, String name, String phone, String state, String address, Date createddate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.address = address;
        this.createddate = createddate;
    }

    /** Retrieves the ID of the supplier. */
    public long getId() {
        return id;
    }

    /** Sets the ID of the supplier. */
    public void setId(long id) {
        this.id = id;
    }

    /** Retrieves the name of the supplier. */
    public String getName() {
        return name;
    }

    /** Sets the name of the supplier. */
    public void setName(String name) {
        this.name = name;
    }

    /** Retrieves the phone number of the supplier. */
    public String getPhone() {
        return phone;
    }

    /** Sets the phone number of the supplier. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Retrieves the address of the supplier. */
    public String getAddress() {
        return address;
    }

    /** Sets the address of the supplier. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Retrieves the state of the supplier. */
    public String getState() {
        return state;
    }

    /** Sets the state of the supplier. */
    public void setState(String state) {
        this.state = state;
    }

    /** Retrieves the date when the supplier was created. */
    public Date getCreateddate() {
        return createddate;
    }

    /** Sets the date when the supplier was created. */
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

}
