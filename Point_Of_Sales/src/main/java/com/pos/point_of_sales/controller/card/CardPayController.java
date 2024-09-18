package com.pos.point_of_sales.controller.card;

import com.pos.point_of_sales.entity.CardPayment;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.Payment;
import com.pos.point_of_sales.entity.PaymentTransaction;
import com.pos.point_of_sales.model.*;
import com.pos.point_of_sales.pdf.PrintInvoice;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;

/**
 * instead of this class  I am using PaymentTransactionController class this is backup controller written earlier for CardPayment
 * @author Mahesh Yadav
 */
public class CardPayController  implements Initializable {

    @FXML
    private Button logoutButton,cancelButton;
    @FXML
    private StackPane paymentPane;
    @FXML
    private StackPane loadingPane;
    @FXML
    private Label paymentStatusLabel;
    private double netPrice;
    private Payment payment;
    private ObservableList<Item> items;
    @FXML
    private TextField totalAmountField, payAmountField;
    @FXML
    private TextField expiryDateField, cvvField, cardNumberField ;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    private double xOffset = 0;
    private double yOffset = 0;
    private String barcode;
    private CardPayModel cardPayModel;
    private CardPayment cardPayment;
    private PaymentTransactionModel paymentTransactionModel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salesModel = new SalesModel();
        cardPayModel = new CardPayModel();
        cardPayment = new CardPayment();
        paymentTransactionModel = new PaymentTransactionModel();
        totalAmountField.setText(String.valueOf(netPrice));
        payAmountField.setText(String.valueOf(netPrice));
    }
    public void setData(double netPrice, ObservableList<Item> items, Payment payment,String barcode) {
        this.netPrice = netPrice;
        this.items = FXCollections.observableArrayList(items);
        this.payment = payment;
        this.barcode = barcode;
    }
    @FXML
    public void processPayment(ActionEvent event) {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();
        if (isValidCardData(cardNumber, expiryDate, cvv)) {
            // If card data is valid, initiate payment process
            showLoadingAnimation();
            //processPayments();
            savePaymentDetails();
            initiatePaymentProcess();
        } else {
            paymentStatusLabel.setText("Invalid card information. Please check and try again.");
        }
    }

   @FXML
    public void processPayments1(ActionEvent event) throws SQLException {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        // payAmountField.setText(String.valueOf(netPrice));
        // double netAmt = Double.parseDouble(payAmountField.getText());
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCardNumber(cardNumber);
        paymentTransaction.setExpiryDate(expiryDate);
        paymentTransaction.setCvv(cvv);
        paymentTransaction.setTotalAmount(totalAmount);
        //  paymentTransaction.setTotalAmount(netAmt);
        paymentTransactionModel.saveTransaction(paymentTransaction);
    }
    private void savePaymentDetails() {
        try {
            if (payment != null)
            {
                cardPayModel.savePayments(cardPayment);
                System.out.println("Payment details saved successfully!");
            } else {
                System.out.println("Payment object is null. Cannot save payment details.");
            }
        } catch (SQLException e)
        {
            System.out.println("Error saving payment details: " + e.getMessage());
            handleSQLException(e);
        }
    }
    private static boolean isValidCardNumber(String cardNumber) {
        // Check if the card no. is not null and is exactly 16 digits
        if (cardNumber != null && cardNumber.length() == 16)
        {
            // Check if the card number contains only numeric chars & ^\d{4}-?\d{4}-?\d{4}-?\d{4}$
            // return cardNumber.matches("^\\d+$") || cardNumber.matches("^\\d{4}-?\\d{4}-?\\d{4}-?\\d{4}$");
            return cardNumber.matches("^\\d+$");
        } else {
            return false;
        }
    }
    private static boolean isValidExpiryDate(String expiryDate) {
        // For example, you might parse the string into a LocalDate object and check if it's in the future
        if (expiryDate == null || expiryDate.isEmpty())
            return false;
        try {
            LocalDate expiry = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern("MM/YY"));
            return !expiry.isBefore(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isValidCVV(String cvv) {
        // return cvv != null && cvv.matches("^\\d{3}$");
        return cvv != null && cvv.matches("^[0-9]{3}$");
    }

    public static boolean isValidCardData(String cardNumber, String expiryDate, String cvv) {
        return !cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty() && isValidCardNumber(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVV(cvv);
    }
    private void initiatePaymentProcess()
    {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), new KeyValue(paymentPane.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(3), event -> showPaymentSuccess())
        );
        timeline.play();
        PrintInvoice pi = new PrintInvoice(items, barcode,payment);
        pi.generateReport();
        ActionEvent event = null;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void showLoadingAnimation() {
        // loading animation while processing payment
        loadingPane.setVisible(true);
    }
    private void showPaymentSuccess() {
        // Hide loading animation and show payment success message
        loadingPane.setVisible(false);
        paymentStatusLabel.setText("Payment successful!");
    }
    @FXML
    public void cancelAction(ActionEvent event)
    {
     /*   cardNumberField.clear();
        expiryDateField.clear();
        cvvField.clear();*/
        cardNumberField.setText("");
        expiryDateField.setText("");
        cvvField.setText("");
        totalAmountField.setText("");
        payAmountField.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Pos.fxml"));
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Inventory:: Version 1.0");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        //stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}


