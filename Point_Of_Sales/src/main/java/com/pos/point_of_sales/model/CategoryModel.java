package com.pos.point_of_sales.model;

import com.pos.point_of_sales.dao.CategoryDao;
import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.populators.CategoryPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.CategoryQueries.*;

/**
 * The CategoryModel class provides methods for interacting with categories in the database,this class implements CategoryDao .
 * It handles CRUD operations for categories.
 */
public class CategoryModel implements CategoryDao {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryModel.class);

    private CategoryPopulator categoryPopulator = new CategoryPopulator();
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    private Category category = null;

    /**
     * Retrieves all Categories from the database.
     *
     * @return an ObservableList of Category objects representing all Categories
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Category> getCategories() throws SQLException {
        ObservableList<Category> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(categoryPopulator.populateCategory(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching categories: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a category by its ID from the db.
     *
     * @param id the ID of the category
     * @return the Category object representing the category with the given ID
     * @throws SQLException
     */
    @Override
    public Category getCategory(long id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category = categoryPopulator.populateCategory(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching category by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return category;
    }

    /**
     * Saves a new category to the database.
     *
     * @param category the Category object representing the category to save
     * @throws SQLException
     */
    @Override
    public void saveCategory(Category category) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_CATEGORY);
            categoryPopulator.saveCategory(preparedStatement, category);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Error occurred while saving category: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category the Category object representing the category to update
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void updateCategory(Category category) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CATEGORY);
            categoryPopulator.categoryUpdate(preparedStatement, category);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating category: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a category from the database.
     *
     * @param category the Category object representing the category to delete
     * @throws SQLException
     */
    @Override
    public void deleteCategory(Category category) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_CATEGORY);
            categoryPopulator.populatePreparedStatementForDelete(preparedStatement, category);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting category: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Retrieves all category types from the database.
     *
     * @return an ObservableList of strings representing all category types
     * @throws SQLException
     */
    @Override
    public ObservableList<String> getTypes() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TYPES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching category types: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }
}
