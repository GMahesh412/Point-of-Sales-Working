package com.pos.point_of_sales.controller.product;


import com.pos.point_of_sales.controller.barcode.BarcodeGenerator;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;

/**
 * AddController  class is for adding a new product into the DB and handles save,validateInput and other related operations of products.
 * @author Mahesh Yadav
 */
public  class AddController implements Initializable, ProductInterface
{
    private static final Logger LOG = LoggerFactory.getLogger(AddController.class);

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
    private BarcodeGenerator barcodeGenerator;
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        productModel = new ProductModel();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        barcodeGenerator = new BarcodeGenerator();

        ObservableList<String> categoryList = null;
        try {
            categoryList = FXCollections.observableArrayList(categoryModel.getTypes());
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching categories: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        ObservableList<String> supplierList = null;
        try {
            supplierList = FXCollections.observableArrayList(supplierModel.getNames());
        } catch (SQLException e) {
            LOG.error("Error occurred while fetching suppliers: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        categoryBox.setItems(categoryList);
        supplierBox.setItems(supplierList);
    }
    /**
     * Handles the save action event , saves the newly added product into the db.
     * @param event The action event
     * @throws SQLException if an SQL exception occurs
     */
    @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput())
        {
            Category category = categoryModel.getCategory(categoryBox.getSelectionModel().getSelectedIndex() + 1);
            Supplier supplier = supplierModel.getSupplier(supplierBox.getSelectionModel().getSelectedIndex() + 1);

            List<Category> categoryList = categoryModel.getCategories();
            ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
            ObservableList<Product> productList = productModel.getProducts();

            try {
                boolean productExists = false;
                for (Product existingProduct : productList)
                {
                    String productName = nameField.getText();
                    if (existingProduct.getProductName().equals(productName))
                    {
                        productExists = true;
                        LOG.debug("Product '{}' already exists.", productName);
                        LOG.info("Product '{}' already exists.", productName);
                        showAlert("Invalid Products", "Please import valid Products that are not existed..", Alert.AlertType.ERROR);
                        break;
                    }
                }

                if (!productExists)
                {
                    java.util.Date dt = new java.util.Date();
                    Product product = new Product(
                            nameField.getText(),
                            Double.parseDouble(priceField.getText()),
                            Double.parseDouble(quantityField.getText()),
                            descriptionArea.getText(),
                            category,
                            supplier,
                            new Date(System.currentTimeMillis())
                    );
                    productModel.saveProduct(product);
                    PRODUCTLIST.clear();
                    String code = barcodeGenerator.barCode();
                    product.setBarcode(code);
                    PRODUCTLIST.addAll(productModel.getProducts());
                    ((Stage) saveButton.getScene().getWindow()).close();
                    showAlert("Successful", "Product is added successfully", Alert.AlertType.INFORMATION);
                    LOG.info("Product '{}' added successfully.", nameField.getText());

                }
            }catch (SQLException ex)
            {
                handleSQLException(ex);
                LOG.error("Error occurred while saving product: {}", ex.getMessage());
            }
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        descriptionArea.setText("");
        categoryBox.valueProperty().setValue(null);
    }

    private boolean validateInput()
    {
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
            showAlert("Invalid Fields", "Please correct invalid fields", Alert.AlertType.ERROR);
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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



/*
public  class AddController implements Initializable, ProductInterface
{

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
    private BarcodeGenerator barcodeGenerator;
    private static final Logger LOG = LoggerFactory.getLogger(AddController.class);


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
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
    }

       @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput())
        {
            Category category = categoryModel.getCategory(categoryBox.getSelectionModel().getSelectedIndex() + 1);
            Supplier supplier = supplierModel.getSupplier(supplierBox.getSelectionModel().getSelectedIndex() + 1);

            List<Category> categoryList = categoryModel.getCategories();
            ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
            ObservableList<Product> productList = productModel.getProducts();

            try {
                boolean productExists = false;
                for (Product existingProduct : productList)
                {
                    String productName = nameField.getText();
                    if (existingProduct.getProductName().equals(productName))
                    {
                        productExists = true;
                        System.out.println("Product '" + productName + "' already exists.");
                        System.out.println("Products already existed or Error occurred in importing Products.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Products");
                        alert.setHeaderText("Please import valid Products that are not existed..");
                        alert.setContentText("Error in importing Categories..");
                        alert.showAndWait();
                        break;
                    }
                }

                if (!productExists)
                {
                   *//* LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    java.util.Date date = new Date(formattedDateTime);*//*

                    //LocalDateTime currentTime = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    java.util.Date dt = new java.util.Date();
                    Product product = new Product(
                            nameField.getText(),
                            Double.parseDouble(priceField.getText()),
                            Double.parseDouble(quantityField.getText()),
                            descriptionArea.getText(),
                            category,
                            supplier,
                            new Date(System.currentTimeMillis())
                    );
                    productModel.saveProduct(product);
                    PRODUCTLIST.clear();
                    String code = new BarcodeGenerator().barCode();
                    product.setBarcode(code);
                    PRODUCTLIST.addAll(productModel.getProducts());
                    ((Stage) saveButton.getScene().getWindow()).close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Product is added");
                    alert.setContentText("Product is added successfully");
                    alert.showAndWait();
                    System.out.println("Product is added successfully...");
                    System.out.println("Product '" + nameField.getText() + "' added successfully.");
                }
            }catch (SQLException ex)
            {
                handleSQLException(ex);
                System.out.println("Product adding is failed...");
            }
        }
    }
    @FXML
    public void handleCancel(ActionEvent event) {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
        descriptionArea.setText("");
        categoryBox.valueProperty().setValue(null);
    }
    private boolean validateInput()
    {
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

    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}*/
