package com.pos.point_of_sales.controller.product;

import com.pos.point_of_sales.controller.barcode.BarcodeGenerator;
import com.pos.point_of_sales.controller.employee.EmployeeController;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * EditController class is for editing products.
 * Handles actions related to editing products in the UI.
 * Author: Mahesh Yadav
 */
public  class EditController implements Initializable, ProductInterface {
    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);
    @FXML
    private TextField nameField, priceField, quantityField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ComboBox categoryBox, supplierBox;
    @FXML
    private Button saveButton;
    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SupplierModel supplierModel;
    private Product product;
    private long selectedProductId;

    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        productModel = new ProductModel();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        new BarcodeGenerator().barCode();

        ObservableList<String> categoryList = null;
        try {
            categoryList = FXCollections.observableArrayList(categoryModel.getTypes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> supplierList = null;
        try {
            supplierList = FXCollections.observableArrayList(supplierModel.getNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        categoryBox.setItems(categoryList);
        supplierBox.setItems(supplierList);
        resetValues();
    }

    public void setProduct(Product product, long selectedProductId) {
        this.product = product;
        this.selectedProductId = selectedProductId;
        setData();
    }

    private void setData() {
        nameField.setText(product.getProductName());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        descriptionArea.setText(String.valueOf(product.getDescription()));

        categoryBox.getSelectionModel().select(((int) product.getCategory().getId()) - 1);
        supplierBox.getSelectionModel().select(((int) product.getSupplier().getId()) - 1);
    }
    /**
     * Handles the save action event , saves the edited product into the db, updates in the UI.
     * @param event The action event
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput()) {
            Category category = categoryModel.getCategory(categoryBox.getSelectionModel().getSelectedIndex() + 1);
            Supplier supplier = supplierModel.getSupplier(supplierBox.getSelectionModel().getSelectedIndex() + 1);
            java.util.Date dt = new java.util.Date();
            Product editedProduct = new Product(
                    product.getBarcode(),
                    product.getId(),
                    category,supplier,nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Double.parseDouble(quantityField.getText()),
                    descriptionArea.getText(),
                    new Date(System.currentTimeMillis())
            );

            productModel.updateProduct(editedProduct);
            PRODUCTLIST.set((int) selectedProductId, editedProduct);
            ((Stage) saveButton.getScene().getWindow()).close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Product Updated!");
            alert.setContentText("Product is updated successfully");
            alert.showAndWait();
            LOG.info("Product is updated successfully" + nameField.getText());
            LOG.debug("Product '{}' updated successfully.", nameField.getText());
        }
    }

    private void resetValues() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        descriptionArea.setText("");
        categoryBox.valueProperty().setValue(null);
        supplierBox.valueProperty().setValue(null);
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        resetValues();
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        }

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "No valid quantity!\n";
        }

        if (descriptionArea.getText() == null || descriptionArea.getText().length() == 0) {
            errorMessage += "No email description!\n";
        }

        if (categoryBox.getSelectionModel().isEmpty()) {
            errorMessage += "Please select the category!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
    /**
     * it Handles the closes the window action event when it is triggered.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
