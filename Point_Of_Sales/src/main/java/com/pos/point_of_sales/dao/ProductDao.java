package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Product;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents a Data Access Object (DAO) interface for managing products in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface ProductDao {

  /**
   * Retrieves a list of all products.
   * @return An ObservableList of Product objects.
   * @throws SQLException if there is an error accessing the database.
   */
  public ObservableList<Product> getProducts() throws SQLException;

  /**
   * Retrieves a product by its ID.
   * @param id The ID of the product to retrieve.
   * @return The Product object corresponding to the given ID.
   * @throws SQLException if there is an error accessing the database.
   */
  public Product getProduct(long id) throws SQLException;

  /**
   * Retrieves a product by its name.
   * @param productName The name of the product to retrieve.
   * @return The Product object corresponding to the given name.
   * @throws SQLException if there is an error accessing the database.
   */
  public Product getProductByName(String productName) throws SQLException;

  /**
   * Saves a new product to the database.
   * @param product The Product object to be saved.
   * @throws SQLException if there is an error accessing the database.
   */
  public void saveProduct(Product product) throws SQLException;

  /**
   * Updates an existing product in the database.
   * @param product The Product object containing updated information.
   * @throws SQLException if there is an error accessing the database.
   */
  public void updateProduct(Product product) throws SQLException;

  /**
   * Decreases the quantity of a product in the database.
   * @param product The Product object representing the product to be decreased.
   * @throws SQLException if there is an error accessing the database.
   */
  public void decreaseProduct(Product product) throws SQLException;

  /**
   * Deletes a product from the database.
   * @param product The Product object to be deleted.
   * @throws SQLException if there is an error accessing the database.
   */
  public void deleteProduct(Product product) throws SQLException;

  /**
   * Retrieves a list of names of all products.
   * @return A List of strings representing product names.
   * @throws SQLException if there is an error accessing the database.
   */
  public List<String> getProductNames() throws SQLException;

  /**
   * Increases the quantity of a product in the database.
   * @param product The Product object representing the product to be increased.
   * @throws SQLException if there is an error accessing the database.
   */
  public void increaseProduct(Product product) throws SQLException;
}
