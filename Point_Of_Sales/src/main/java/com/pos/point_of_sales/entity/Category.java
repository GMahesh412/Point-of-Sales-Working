package com.pos.point_of_sales.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a category in the Point of Sales system.
 * @author Mahesh Yadav
 */
public class Category implements Serializable {

    private long id;
    private String type;
    private String description;
    private Date createddate;

    /**
     * Default constructor for Category.
     */
    public Category() {
    }

    /**
     * Constructs a Category with specified parameters.
     *
     * @param type        The type of the category.
     * @param description The description of the category.
     * @param createddate The date when the category was created.
     */
    public Category(String type, String description, Date createddate) {
        this.type = type;
        this.description = description;
        this.createddate = createddate;
    }

    /**
     * Constructs a Category with specified parameters.
     *
     * @param id          The ID of the category.
     * @param type        The type of the category.
     * @param description The description of the category.
     * @param createddate The date when the category was created.
     */
    public Category(long id, String type, String description, Date createddate) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.createddate = createddate;
    }

    /**
     * Gets the ID of the category.
     *
     * @return The ID of the category.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the category.
     *
     * @param id The ID of the category.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the type of the category.
     *
     * @return The type of the category.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the category.
     *
     * @param type The type of the category.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the description of the category.
     *
     * @return The description of the category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     *
     * @param description The description of the category.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the date when the category was created.
     *
     * @return The date when the category was created.
     */
    public Date getCreateddate() {
        return createddate;
    }

    /**
     * Sets the date when the category was created.
     *
     * @param createddate The date when the category was created.
     */
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }


}
