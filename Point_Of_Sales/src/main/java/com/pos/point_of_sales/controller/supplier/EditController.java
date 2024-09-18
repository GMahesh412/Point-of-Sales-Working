package com.pos.point_of_sales.controller.supplier;

import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.SupplierInterface;
import com.pos.point_of_sales.model.SupplierModel;
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
 * EditController class handles editing of supplier details.
 * It implements the Initialize and SupplierInterface.
 * @author Mahesh Yadav
 */
public class EditController implements Initializable, SupplierInterface {
    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);

    @FXML
    private TextField supplierField, phoneField,stateField;
    @FXML
    private TextArea addressArea;
    @FXML
    private Button saveButton;
    private long selectedSupplierId;
    private SupplierModel supplierModel;
    private Supplier supplier;
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierModel = new SupplierModel();
        resetValues();
    }
    /**
     * Resets the input fields to default values.
     */
    private void resetValues() {

        supplierField.setText("");
        phoneField.setText("");
        stateField.setText("");
        addressArea.setText("");
    }
    /**
     * Sets the data for editing the supplier.
     */
    public void setSupplier(Supplier supplier, long selectedSupplierId){
        this.supplier = supplier;
        this.selectedSupplierId = selectedSupplierId;
        setData();
    }

    /**
     * it fills the input fields with supplier data for editing.
     */
    private void setData(){
        supplierField.setText(supplier.getName());
        phoneField.setText(supplier.getPhone());
        stateField.setText(supplier.getState());
        addressArea.setText(supplier.getAddress());
    }
    /**
     * Handles the cancel action event.
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        resetValues();
    }
    /**
     * Handles the save action event of the selected supplier to be edited in database and UI.
     */
    @FXML
    public void handleSave(ActionEvent event) throws SQLException {
try
    {
        if (validateInput())
        {
            Supplier editedSupplier = new Supplier(
                    supplier.getId(),
                    supplierField.getText(),
                    phoneField.getText(),
                    stateField.getText(),
                    addressArea.getText(),
                    new Date(System.currentTimeMillis())
            );

            supplierModel.updateSupplier(editedSupplier);
            SUPPLIERLIST.set((int) selectedSupplierId, editedSupplier);
            ((Stage) saveButton.getScene().getWindow()).close();
            showAlert("Successful", "Supplier Updated!", "Supplier is updated successfully");
        }
    } catch (SQLException e) {
        LOG.error("Error occurred while saving supplier: {}", e.getMessage());
        showAlert("Error", "Database Error", "Error occurred while updating supplier details.");
    }
}
   @FXML
    private void validatePhone()
    {
        TextField phoneField = new TextField();
        String phone = phoneField.getText();

        if (phone.matches("\\+91[0-9]{10}") || (phone.matches("[0-9]{10}") && !phone.matches("\\d*"))) {
            // Valid phone number with or without +91
        } else {
            //for Invalid phone number
            validateInput();
        }
    }
    /**
     * Validates the input fields.
     *
     * @return true if input is valid,  otherwise return false
     */
    @FXML
    private boolean validateInput()
    {
        String errorMessage = "";
        if (supplierField.getText() == null || supplierField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0 || phoneField.getText().length() != 10 && phoneField.getText().length() != 13)
        {
            errorMessage += "Invalid Phone Number!\n";
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
            showAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
            return false;
        }
    }

    /**
     * this action Closes the window when it triggered.
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * it will Display an alert dialog.
     *
     * @param title   the title of the alert
     * @param header  the header text of the alert
     * @param content the content text of the alert
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
