package com.pos.point_of_sales.controller.supplier;

import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.SupplierInterface;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import java.util.Date;
import java.sql.SQLException;

import java.util.ResourceBundle;

/**
 * AddController class for adding a new supplier.
 * Handles the action event for adding the suppliers from the UI.
 * It handles saving the supplier into the database and to the UI.
 * Validations for phone number & input are implemented.
 * Author: Mahesh Yadav
 */
public class AddController implements Initializable, SupplierInterface {
    private static final Logger LOG = LoggerFactory.getLogger(AddController.class);


    @FXML
    private TextField supplierField, phoneField,stateField;
    @FXML
    private TextArea addressArea;
    @FXML
    private Button saveButton;
    private SupplierModel supplierModel;
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierModel = new SupplierModel();
    }

    /**
     * Handles the save action of supplier.
     * @param event The action event.
     * @throws SQLException If an SQL exception occurs.
     */
    @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput())
        {
            ObservableList<Supplier> supplierList = supplierModel.getSuppliers();
            validatePhone();
            boolean matchFound = false;

            for (Supplier supplier : supplierList) {
                if (supplier.getName().equals(supplierField.getText()))
                {
                    matchFound = true;
                    LOG.debug("Supplier '{}' already exists.", supplierField.getText());
                    LOG.warn("Supplier '{}' already exists.", supplierField.getText());
                    LOG.error("Supplier already exists.");
                    LOG.info("Supplier '" + supplierField.getText() + "' already exists.");
                    showAlert("Invalid Supplier", "Supplier already exists. Please enter a Unique Supplier Name.");
                    break;
                }
            }
            if (!matchFound) {
                Supplier supplier = new Supplier(
                        supplierField.getText(),
                        phoneField.getText(),
                        stateField.getText(),
                        addressArea.getText(),
                        new Date(System.currentTimeMillis())
                );

                supplierModel.saveSupplier(supplier);
                SUPPLIERLIST.clear();
                SUPPLIERLIST.addAll(supplierModel.getSuppliers());

                ((Stage) saveButton.getScene().getWindow()).close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Supplier Added!");
                alert.setContentText("Supplier is Added successfully");
                alert.showAndWait();
                LOG.debug("Supplier '" + supplierField.getText() + "' added successfully !!");
                LOG.info("Supplier successfully added !!");
                LOG.info("Supplier '{}' added successfully.", supplierField.getText());
            }
        }
    }

    @FXML
    private void validatePhone() {
        TextField phoneField = new TextField();
        String phone = phoneField.getText();
//        if (phone.matches("\\+91[0-9]{10}") || (phone.matches("[0-9]{10}") && !phone.matches("\\d*")))
        if (!phone.matches("\\d{10}"))
        {
            showAlert("Invalid Phone Number", "Please enter a valid 10-digit phone number.");
        }
        else {
            validateInput();
        }
    }
    /**
     * it will Display an alert dialog.
     * @param title   the title of the alert
     * @param content the content text of the alert
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        //   alert.showAndWait();
    }

    /**
     * Validates the input fields.
     *
     * @return true if input is valid,  otherwise return false
     */
    private boolean validateInput() {

        String errorMessage = "";

        if (supplierField.getText() == null || supplierField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }

        if (phoneField.getText() == null || phoneField.getText().length() == 0 || phoneField.getText().length() != 10 && phoneField.getText().length() != 13)
        {
            errorMessage = " "+ phoneField.getText() + "Invalid Phone Number!\n";
           LOG.info( phoneField.getText() + "Invalid Phone Number!!" + "Please enter a valid 10-digit phone number.");

        }
        if (stateField.getText() == null || stateField.getText().length() == 0) {
            errorMessage += "No valid state given!\n";
        }

        if (addressArea.getText() == null || addressArea.getText().length() == 0) {
            errorMessage += "No email address!\n";
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
     * this action Closes the window when it triggered.
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    /**
     * Handles the cancel action.
     * @param event The action event.
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        supplierField.setText("");
        phoneField.setText("");
        stateField.setText("");
        addressArea.setText("");
    }
}
