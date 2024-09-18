package com.pos.point_of_sales.controller.report;

import com.pos.point_of_sales.entity.Invoice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ViewController is for view report-related  operations.
 * @author Mahesh Yadav
 */

public class ViewController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @FXML
    private TextField employeeField, totalField, sgstField, cgstField, discountField, payableField, paidField, returnedField;
    @FXML
    private Label serialLabel, dateLabel;
    private Invoice invoice;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setReport(Invoice invoice){
        this.invoice = invoice;
        setData();
    }
    private void setData() {
        serialLabel.setText("Transaction ID# " + invoice.getId());
        dateLabel.setText("Date: " + invoice.getDatetime());

        // Check if employee ID is not null, then set it
        String employeeId = String.valueOf(invoice.getEmployee().getId());
        employeeField.setText(employeeId != null ? employeeId : "Not Available");

        totalField.setText(String.valueOf(invoice.getTotal()));
        sgstField.setText(String.valueOf(invoice.getSgst()));
        cgstField.setText(String.valueOf(invoice.getCgst()));
        discountField.setText(String.valueOf(invoice.getDiscount()));
        payableField.setText(String.valueOf(invoice.getPayable()));
        paidField.setText(String.valueOf(invoice.getPaid()));
        returnedField.setText(String.valueOf(invoice.getReturned()));
    }
/*    private void setData() {
        serialLabel.setText("Transcation ID# " + invoice.getId());
        dateLabel.setText("Date: " + invoice.getDatetime());
        employeeField.setText(invoice.getEmployee().getType());
        totalField.setText(String.valueOf(invoice.getTotal()));
        sgstField.setText(String.valueOf(invoice.getSgst()));
        cgstField.setText(String.valueOf(invoice.getCgst()));
        discountField.setText(String.valueOf(invoice.getDiscount()));
        payableField.setText(String.valueOf(invoice.getPayable()));
        paidField.setText(String.valueOf(invoice.getPaid()));
        returnedField.setText(String.valueOf(invoice.getReturned()));
    }*/
    
    @FXML
    public void handlePrint(ActionEvent event) {

        
    }
    
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
