package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Category;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing categories in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface CategoryDao {

    /**
     * Retrieves a list of all categories.
     * @return An ObservableList of Category objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Category> getCategories() throws SQLException;

    /**
     * Retrieves a category by its ID.
     * @param id The ID of the category to retrieve.
     * @return The Category object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Category getCategory(long id) throws SQLException;

    /**
     * Saves a new category to the database.
     * @param category The Category object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveCategory(Category category) throws SQLException;

    /**
     * Updates an existing category in the database.
     * @param category The Category object to be updated.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updateCategory(Category category) throws SQLException;

    /**
     * Deletes a category from the database.
     * @param category The Category object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteCategory(Category category) throws SQLException;

    /**
     * Retrieves a list of all category types.
     * @return An ObservableList of strings representing category types.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<String> getTypes() throws SQLException;

}
