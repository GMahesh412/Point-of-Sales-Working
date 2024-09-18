package com.pos.point_of_sales.controller.employee;

import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Employee;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.EmployeeInterface;
import com.pos.point_of_sales.model.EmployeeModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

public class AddController implements Initializable, EmployeeInterface {

    @FXML
    private TextField firstField, lastField, usernameField, phoneField,typeField,idField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextArea addressArea;
    @FXML
    private Button saveButton;
    private EmployeeModel employeeModel;
    @FXML
    private ComboBox employeeBox;
    private Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeModel = new EmployeeModel();

    }

    @FXML
    public void handleCancel(ActionEvent event) {
        firstField.setText("");
        lastField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        typeField.setText("");
    }

    @FXML
    public void handleSave(ActionEvent event) throws SQLException {

        if (validateInput()) {
            Employee employee = new Employee(
                    firstField.getText(),
                    lastField.getText(),
                    usernameField.getText(),
                    DigestUtils.sha1Hex(passwordField.getText()),
                    phoneField.getText(),
                    addressArea.getText()
            );

            employeeModel.saveEmployee(employee);
            EMPLOYEELIST.clear();
            EMPLOYEELIST.addAll(employeeModel.getEmployees());

            ((Stage) saveButton.getScene().getWindow()).close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Employee Created!");
            alert.setContentText("Employee is created successfully");
            alert.showAndWait();
        }
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (firstField.getText() == null || firstField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }

        if (lastField.getText() == null || lastField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }

        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "No valid phone number!\n";
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

    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
