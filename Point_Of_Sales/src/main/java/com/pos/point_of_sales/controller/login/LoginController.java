package com.pos.point_of_sales.controller.login;

import com.pos.point_of_sales.model.EmployeeModel;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.pos.point_of_sales.model.ProductModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoginController class for handling the login functionality.
 * For both admin & employee panels.
 * @author Mahesh Yadav
 */
public class LoginController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private TextField usernameField, passwordField;
    private EmployeeModel model;

    @FXML
    private Label invalidDetails;
    @FXML
    private ImageView usersIcon, passwordIcon;

    /**
     * Initializes the controller.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new EmployeeModel();
        enterPressed();
    }

    private void enterPressed() {

        usernameField.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    authenticate(ke);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        passwordField.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    authenticate(ke);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    public void loginAction(ActionEvent event) throws Exception {

        authenticate(event);
    }

   /* private void authenticate(Event event) throws Exception
    {

        String username = usernameField.getText().trim();
        String password = DigestUtils.sha1Hex(passwordField.getText().trim());

        if (model.checkUser(username))
        {
            if (model.checkPassword(username, password))
            {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                String type = model.getEmployeeType(username);
                LOG.debug("Employee type is " + type + "Username is :" + username + "Password is :" + password);
                switch (type) {
                    case "admin":
                        windows("/fxml/Admin.fxml", "Admin Panel");
                        break;
                    case "employee":
                        windows("/fxml/Pos.fxml", "Point of Sales");
                        break;
                }
            } else {
                passwordField.setText("");
                passwordField.setStyle(errorStyle);
                invalidDetails.setText("The Password is required!");
                LOG.error("The Password is required! Password should not be blank or empty!!");
                usernameField.setStyle(successStyle);
                new animatefx.animation.Shake(passwordField).play();
                new animatefx.animation.Wobble(passwordIcon).play();
            }
        } else {
            resetFields();
            usernameField.setStyle(errorStyle);
            invalidDetails.setText("User doesn't exist! The Username is required!");
            LOG.error("The Username is required! Username should not be blank or empty!!");
            passwordField.setStyle(successStyle);
            new animatefx.animation.Shake(usernameField).play();
            new animatefx.animation.Pulse(usersIcon).play();
        }
    }
*/

   private void authenticate(Event event) throws Exception {
        if (validateInput()) {

            String username = usernameField.getText().trim();
            String password = DigestUtils.sha1Hex((passwordField.getText().trim()));

            if (model.checkUser(username))
            {
                if (model.checkPassword(username, password))
                {
                    //   model = employeeModel.getEmployee()
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    String type = model.getEmployeeType(username);
                    LOG.debug("Employee type is " +type + "Username is :"+username +"Password is :"+password);
                    switch (type) {
                        case "admin":
                            windows("/fxml/Admin.fxml", "Admin Panel");
                            break;

                        case "employee":
                            windows("/fxml/Pos.fxml", "Point of Sales");
                            break;
                    }
                } else {
                    passwordField.setText("");
                    if (passwordField.getText().isBlank())
                    {
                        passwordField.setStyle(errorStyle);
                        invalidDetails.setText("The Password is required!");
                        LOG.error("The Password is required! Password should not be blank or empty!!");
                        usernameField.setStyle(successStyle);
                        new animatefx.animation.Shake(passwordField).play();
                        new animatefx.animation.Wobble(passwordIcon).play();
                    }
                }
            } // When only the username is blank
            else
                resetFields();
            if (usernameField.getText().isBlank())
            {
                usernameField.setStyle(errorStyle);
                invalidDetails.setText("User doesn't exist! The Username is required!");
                LOG.warn(username + "'User doesn't exist! The Username is required!");
                LOG.error("The Username is required! Username should not be blank or empty!!");
                passwordField.setStyle(successStyle);
                new animatefx.animation.Shake(usernameField).play();
                new animatefx.animation.Pulse(usersIcon).play();
            }/*else {
                resetFields();
                invalidDetails.setText("User doesn't exist!");
            }*/
        }
    }

    private void windows(String path, String title) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tdsitelogo.png")));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        resetFields();
    }

    @FXML
    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void minusAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private boolean validateInput() {
        // In case the Username and Password fields are left blank then display the error message
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            invalidDetails.setStyle(errorMessage);

            // When the username and password are blank
            if (usernameField.getText().isBlank() && passwordField.getText().isBlank()) {
                invalidDetails.setText("The Login fields are required!");
                LOG.error("The Username & Password is required! Login fields are should not be blank or empty!!");
                usernameField.setStyle(errorStyle);
                passwordField.setStyle(errorStyle);

                new animatefx.animation.Shake(usernameField).play();
                new animatefx.animation.Wobble(usersIcon).play();
                new animatefx.animation.Shake(passwordField).play();
                new animatefx.animation.Wobble(passwordIcon).play();

            } else // When only the username is blank
                if (usernameField.getText().isBlank()) {
                    usernameField.setStyle(errorStyle);
                    invalidDetails.setText("The Username or Email is required!");
                    passwordField.setStyle(successStyle);
                    new animatefx.animation.Shake(usernameField).play();
                    new animatefx.animation.Pulse(usersIcon).play();
                } else // When only the password is blank
                    if (passwordField.getText().isBlank()) {
                        passwordField.setStyle(errorStyle);
                        invalidDetails.setText("The Password is required!");
                        usernameField.setStyle(successStyle);
                        new animatefx.animation.Shake(passwordField).play();
                        new animatefx.animation.Wobble(passwordIcon).play();
                    }
        }
        String invalidDetails = "";
        if (invalidDetails.length() == 0) {
            return true;
        }

        return false;
    }
}





/* private void authenticate(Event event) throws Exception {
        String username = usernameField.getText().trim();
        String password = DigestUtils.sha1Hex(passwordField.getText().trim());

        if (username.isEmpty() && password.isEmpty()) {
            LOG.error("Both Username and Password are required!");
            usernameField.setStyle(errorStyle);
            passwordField.setStyle(errorStyle);
            new animatefx.animation.Shake(usernameField).play();
            new animatefx.animation.Pulse(usersIcon).play();
            new animatefx.animation.Shake(passwordField).play();
            new animatefx.animation.Wobble(passwordIcon).play();
            return;
        }

        if (username.isEmpty()) {
            LOG.error("Username is required!");
            usernameField.setStyle(errorStyle);
            passwordField.setStyle(successStyle);
            invalidDetails.setText("Username is required!");
            LOG.error("The Username is required! Username should not be blank or empty!!");
            new animatefx.animation.Shake(usernameField).play();
            new animatefx.animation.Pulse(usersIcon).play();
            return;
        }

        if (password.isEmpty()) {
            LOG.error("Password is required!");
            usernameField.setStyle(successStyle);
            passwordField.setStyle(errorStyle);
            invalidDetails.setText("Password is required!");
            LOG.error("The Password is required! Password should not be blank or empty!!");
            new animatefx.animation.Shake(passwordField).play();
            new animatefx.animation.Wobble(passwordIcon).play();
            return;
        }

        if (!model.checkUser(username))
        {
            resetFields();
            usernameField.setStyle(errorStyle);
            invalidDetails.setText("User doesn't exist! The Username is required!");
            LOG.error("The Username is required!'" + username +"' doesn't exist in the system!");
            LOG.debug("The Username is required!'" + username + "' doesn't exist in the system!");
            passwordField.setStyle(successStyle);
            new animatefx.animation.Shake(usernameField).play();
            new animatefx.animation.Pulse(usersIcon).play();
            return;
        }

        if (!model.checkPassword(username, password)) {
            passwordField.setText("");
            passwordField.setStyle(errorStyle);
            invalidDetails.setText("Invalid Password!");
           // LOG.error("Invalid Password! Password should not be blank or empty!!");
            LOG.error("Invalid Password!  The '" + password + "'is doesn't exist in the system!");
            LOG.debug("The Password is required!'" + password + " 'doesn't exist in the system!");
            usernameField.setStyle(successStyle);
            new animatefx.animation.Shake(passwordField).play();
            new animatefx.animation.Wobble(passwordIcon).play();
            return;
        }

        ((Node) (event.getSource())).getScene().getWindow().hide();
        String type = model.getEmployeeType(username);
        LOG.debug("Employee type is " + type + "Username is :" + username + "Password is :" + password);
        switch (type) {
            case "admin":
                windows("/fxml/Admin.fxml", "Admin Panel");
                break;
            case "employee":
                windows("/fxml/Pos.fxml", "Point of Sales");
                break;
        }
    }
*/