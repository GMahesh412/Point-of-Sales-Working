package com.pos.point_of_sales.queries;


/**
 * Contains SQL queries related to categories.
 * @author Mahesh_Yadav
 */
public class CategoryQueries {

    /**
     * SQL query to select all categories.
     */
    public static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Categories";

    /**
     * SQL query to select a category by its ID.
     */
    public static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM Categories WHERE id = ?";

    /**
     * SQL query to insert a new category into the database.
     */
    public static final String INSERT_CATEGORY = "INSERT INTO Categories (id, type, description, createddate) VALUES (?, ?, ?, ?)";

    /**
     * SQL query to update an existing category in the database.
     */
    public static final String UPDATE_CATEGORY = "UPDATE Categories SET type = ?, description = ?, createddate = ? WHERE id = ?";

    /**
     * SQL query to delete a category from the database.
     */
    public static final String DELETE_CATEGORY = "DELETE FROM Categories WHERE id = ?";

    /**
     * SQL query to select all category types.
     */
    public static final String SELECT_TYPES = "SELECT type FROM Categories";

}
