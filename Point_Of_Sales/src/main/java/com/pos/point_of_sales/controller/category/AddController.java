package com.pos.point_of_sales.controller.category;


import com.pos.point_of_sales.controller.admin.AdminController;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.interfaces.CategoryInterface;
import com.pos.point_of_sales.model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

import java.util.Date;
import java.sql.SQLException;

import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * AddController  class is for adding a new category into the DB and handles save,validateInput and other related operations of categories.
 * @author Mahesh Yadav
 */
public class AddController implements Initializable, CategoryInterface {

    private static final Logger LOG = LoggerFactory.getLogger(AddController.class);

    @FXML
    private TextField typeField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button saveButton;
    private CategoryModel categoryModel;

    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        categoryModel = new CategoryModel();
    }

    /**
     * Handles the cancel action event.
     *
     * @param event The action event
     */
    @FXML
    public void handleCancel(ActionEvent event)
    {
        typeField.setText("");
        descriptionArea.setText("");
    }
    /**
     * Handles the save action event , saves the category in db.
     *
     * @param event The action event
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    public void handleSave(ActionEvent event) throws SQLException {
        if (validateInput()) {
            ObservableList<Category> categoryList = categoryModel.getCategories();
            boolean matchFound = false;

            for (Category category : categoryList) {
                if (category.getType().equals(typeField.getText())) {
                    matchFound = true;
                    LOG.warn("Category '{}' adding failed!!", typeField.getText());
                    LOG.warn("Category already existed or Error adding Category...");
                    LOG.error("Category already existed or Error adding Category...");
                    break;
                }
            }

            if (matchFound)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Category");
                alert.setHeaderText(typeField.getText() + " Category already exists");
                alert.setContentText("Please enter a Unique Category type.");
                alert.showAndWait();
            } else {
                Category category = new Category(
                        typeField.getText(),
                        descriptionArea.getText(),
                        new Date(System.currentTimeMillis())
                );
                categoryModel.saveCategory(category);
                CATEGORYLIST.clear();
                CATEGORYLIST.addAll(categoryModel.getCategories());
                ((Stage) saveButton.getScene().getWindow()).close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Category Created");
                alert.setContentText("Category has been successfully created.");
                alert.showAndWait();
                LOG.info("Category '{}' added successfully!!", typeField.getText());
                LOG.info("Category added successfully!!");
            }
        }
    }
    /**
     * Validates the input fields.
     *
     * @return True if the input is valid, otherwise false
     */
    private boolean validateInput()
    {
        String errorMessage = "";
        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }

        if (descriptionArea.getText() == null || descriptionArea.getText().length() == 0) {
            errorMessage += "No email description!\n";
        }

        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    /**
     * Closes the current window.
     *
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
