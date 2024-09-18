package com.pos.point_of_sales.controller.pos;


import com.pos.point_of_sales.controller.login.LoginController;
import com.pos.point_of_sales.entity.*;
import com.pos.point_of_sales.model.EmployeeModel;
import com.pos.point_of_sales.model.InvoiceModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SalesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * InvoiceController class for handling invoice-related actions and UI interactions.
 * It will send data to ConfirmController for further actions.
 * @author Mahesh Yadav
 */
public class InvoiceController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceController.class);


    @FXML
    private TextField totalAmountField, paidAmountField;
    private double netPrice;
    private ObservableList<Item> items;
    private EmployeeModel employeeModel;
    private ProductModel productModel;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    private Payment payment;
    private Product product;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Initializes the controller class.
     *
     * @param url      The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productModel = new ProductModel();
        employeeModel = new EmployeeModel();
        salesModel = new SalesModel();
        invoiceModel = new InvoiceModel();
        totalAmountField.setText(String.valueOf(Math.round(netPrice)));
    }

    /**
     * setData Sets data for the invoice.
     *
     * @param netPrice The total amount of the invoice
     * @param items    The list of items in the invoice
     * @param payment  The payment details
     */
    public void setData(double netPrice, ObservableList<Item> items, Payment payment) {
        this.netPrice = netPrice;
        this.items = FXCollections.observableArrayList(items);
        this.payment = payment;
    }
    /**
     * confirmAction Handles the action event for confirming the payment.
     *
     * @param event The action event
     * @throws Exception If an error occurs during payment confirmation
     */
   @FXML
    public void confirmAction(ActionEvent event) throws Exception {
        LOG.debug("Confirming payment...");
       if (validateInput())
       {
           double paid = Double.parseDouble(paidAmountField.getText().trim());
           double retail = Math.abs(paid - netPrice);

           String id = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

           Invoice invoice = new Invoice(
                   id,
                   employeeModel.getEmployee(2),
                   payment.getSubTotal(),
                   payment.getSgst(),
                   payment.getCgst(),
                   payment.getDiscount(),
                   payment.getPayable(),
                   paid,
                   retail,
                   new Date(System.currentTimeMillis())
           );
           invoiceModel.saveInvoice(invoice);
           for (Item i : items)
           {
               Product product = productModel.getProductByName(i.getItemName());
               double quantity = product.getQuantity() - i.getQuantity();
               product.setQuantity(quantity);
               productModel.decreaseProduct(product);

               Sale sale = new Sale(
                       invoiceModel.getInvoice(id),
                       product,
                       i.getQuantity(),
                       i.getUnitPrice(),
                       payment.getPayable(),
                       new Date(System.currentTimeMillis())
               );
               salesModel.saveSale(sale);
           }

           FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Confirm.fxml")));
           ConfirmController controller = new ConfirmController();
           controller.setData(retail, items, id, payment);
           loader.setController(controller);
           Parent root = loader.load();
           Scene scene = new Scene(root, 600, 500);
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           root.setOnMousePressed((MouseEvent e) -> {
               xOffset = e.getSceneX();
               yOffset = e.getSceneY();
           });
           root.setOnMouseDragged((MouseEvent e) -> {
               stage.setX(e.getScreenX() - xOffset);
               stage.setY(e.getScreenY() - yOffset);
           });
           stage.setTitle("Payment Confirm");
           Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
           stage.getIcons().add(image);
           stage.setScene(scene);
           stage.show();
       }
   }
    /**
     * Handles the action event for printing the invoice.
     *
     * @param event The action event
     * @throws Exception If an error occurs during invoice printing
     */
    public void printAction(ActionEvent event) throws Exception {

        LOG.debug("Printing invoice...");
        if (validateInput())
        {
            double paid = Double.parseDouble(paidAmountField.getText().trim());
            double retail = Math.abs(paid - netPrice);

            String id = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

            Invoice invoice = new Invoice(
                    id,
                    employeeModel.getEmployee(2),
                    payment.getSubTotal(),
                    payment.getSgst(),
                    payment.getCgst(),
                    payment.getDiscount(),
                    payment.getPayable(),
                    paid,
                    retail,
                    new Date(System.currentTimeMillis())
            );
            invoiceModel.saveInvoice(invoice);

            for (Item i : items) {
                Product p = productModel.getProductByName(i.getItemName());
                double quantity = p.getQuantity() - i.getQuantity();
                p.setQuantity(quantity);
                productModel.decreaseProduct(p);

                Sale sale = new Sale(
                        invoiceModel.getInvoice(id),
                        productModel.getProductByName(i.getItemName()),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getTotal()
                );
                salesModel.saveSale(sale);
            }
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/PrintInvoice.fxml")));
            ConfirmController controller = new ConfirmController();
            controller.setData(retail, items, id, payment);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.setOnMousePressed((MouseEvent e) -> {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent e) -> {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            });
            stage.setTitle("Confirm");
            Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Validates the input for the paid amount.
     *
     * @return True if the input is valid, otherwise false
     */
    private boolean validateInput() {
        String errorMessage = "";
        if (paidAmountField.getText() == null || paidAmountField.getText().length() == 0) {
            errorMessage += "Invalid Input!\n";
        } else if (Double.parseDouble(paidAmountField.getText()) < netPrice) {
            errorMessage += "Insufficient Input!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid amount");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            paidAmountField.setText("");

            return false;
        }
    }
    /**
     * Closes the current window.
     *
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
