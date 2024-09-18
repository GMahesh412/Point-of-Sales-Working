package com.pos.point_of_sales.controller.purchase;

import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Purchase;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.PurchaseInterface;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.PurchaseModel;
import com.pos.point_of_sales.model.SupplierModel;
import java.net.URL;

import java.util.Date;
import java.sql.SQLException;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController implements Initializable, PurchaseInterface {

    @FXML
    private ComboBox productBox, supplierBox;
    @FXML
    private TextField quantityField, priceField;
    @FXML
    private Button saveButton;
    private ProductModel productModel;
    private SupplierModel supplierModel;
    private PurchaseModel purchaseModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productModel = new ProductModel();
        supplierModel = new SupplierModel();
        purchaseModel = new PurchaseModel();
        ObservableList<String> productList = null;
        try {
            productList = FXCollections.observableArrayList(productModel.getProductNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> supplierList = null;
        try {
            supplierList = FXCollections.observableArrayList(supplierModel.getNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productBox.setItems(productList);
        supplierBox.setItems(supplierList);
    }

    @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput())
        {
            Product product = productModel.getProduct(productBox.getSelectionModel().getSelectedIndex() + 1);
            Supplier supplier = supplierModel.getSupplier(supplierBox.getSelectionModel().getSelectedIndex() + 1);
            double quantity = Double.parseDouble(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double total = quantity * price;
            Purchase purchase = new Purchase(
                    product,
                    supplier,
                    quantity,
                    price,
                    total,
                    new Date(System.currentTimeMillis())
            );

            Product updatingProduct = productModel.getProduct(product.getId());
            updatingProduct.setQuantity(updatingProduct.getQuantity() + quantity);
            productModel.increaseProduct(updatingProduct);

            purchaseModel.savePurchase(purchase);
            PURCHASELIST.clear();
            PURCHASELIST.addAll(purchaseModel.getPurchases());

            ((Stage) saveButton.getScene().getWindow()).close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Purchase Completed");
            alert.setContentText("Product is added successfully");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        priceField.setText("");
        quantityField.setText("");
        productBox.valueProperty().setValue(null);
        supplierBox.valueProperty().setValue(null);
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid price!\n";
        }

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "No valid quantity!\n";
        }

        if (productBox.getSelectionModel().isEmpty()) {
            errorMessage += "Please select the product!\n";
        }

        if (supplierBox.getSelectionModel().isEmpty()) {
            errorMessage += "Please select the supplier!\n";
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
}
