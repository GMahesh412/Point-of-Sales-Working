package com.pos.point_of_sales.controller.payment;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;

import com.pos.point_of_sales.controller.login.LoginController;
import com.pos.point_of_sales.controller.pos.ConfirmController;
import com.pos.point_of_sales.controller.supplier.AddController;
import com.pos.point_of_sales.entity.*;
import com.pos.point_of_sales.pdf.PrintInvoice;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import com.pos.point_of_sales.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ResourceBundle;

/* instead of CardPayController,i'm using this class : "PaymentTransactionController class "  */

/**
 * PaymentTransactionController class  is for managing payment transactions.
 * This class will manage all Card Pay transactions and database operations after CardPayment is Successfull.
 * @author Mahesh Yadav
 */
public class PaymentTransactionController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentTransactionController.class);

    @FXML
    private TextField totalAmountField, payAmountField;
    @FXML
    public TextArea billingArea;
    @FXML
    private TextField expiryDateField, cvvField, cardNumberField ;
    @FXML
    private Button logoutButton,cancelButton,payButton,printButton;
    private double xOffset = 0;
    private double yOffset = 0;
    private String barcode;
    @FXML
    private Label retailLabel;
    @FXML
    private Label paymentStatusLabel;
    private double netPrice;
    private Payment payment;
    private ObservableList<Item> items;
    private EmployeeModel employeeModel;
    private ProductModel productModel;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    private PaymentTransaction paymentTransaction;
    private PaymentTransactionModel paymentTransactionModel;
    private ConfirmController confirmController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salesModel = new SalesModel();
        employeeModel = new EmployeeModel();
        invoiceModel = new InvoiceModel();
        productModel = new ProductModel();
        paymentTransactionModel =  new PaymentTransactionModel();
        paymentTransaction = new PaymentTransaction();
        totalAmountField.setText(String.valueOf(netPrice));
        payAmountField.setText(String.valueOf(netPrice));
        confirmController = new ConfirmController();
    }
    /**
     * Sets the data for payment transactions.
     *
     * @param netPrice
     * @param items -list of items
     * @param payment
     * @param barcode
     */
    public void setData(double netPrice, ObservableList<Item> items, Payment payment, String barcode) {
        this.netPrice = netPrice;
        this.items = FXCollections.observableArrayList(items);
        this.payment = payment;
        this.barcode = barcode;
    }

   @FXML
    public void processPayments( ) throws SQLException, IOException
    {
        Task<Void> paymentTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulate payment processing
                Thread.sleep(2000);
                return null;
            }
        };

        paymentTask.setOnSucceeded( event -> Platform.runLater(() -> {
            try {
                displayPaymentSuccessMessage();
            } catch (IOException | SQLException e) {
                handleSQLException((SQLException) e);
            }
        }));

        // Show processing payment message
        showPaymentProcessingMessage(paymentTask);
        new Thread(paymentTask).start();

        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());

        if (!isValidCreditCard(cardNumber))
        {
            showAlert("Invalid Card Number", "Please enter a valid 16-digit credit card number.");
            return;
        }

        if (!isValidExpiryDate(expiryDate))
        {
            showAlert("Invalid Expiry Date", "Please enter a valid expiry date in MM/YY format.");
            return;
        }

        if (!isValidCVV(cvv))
        {
            showAlert("Invalid CVV", "Please enter a valid 3-digit CVV number.");
            return;
        }

        if (isValidCardData(cardNumber, expiryDate, cvv))
        {
            double paid = Double.parseDouble(payAmountField.getText().trim());
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
                Product p = productModel.getProductByName(i.getItemName());
                double quantity = p.getQuantity() - i.getQuantity();
                p.setQuantity(quantity);
                productModel.decreaseProduct(p);
                Sale sale = new Sale(
                         invoiceModel.getInvoice(id),
                         productModel.getProductByName(i.getItemName()),
                        //  productModel.getProduct(Long.parseLong(id)),
                        // productModel.getProducts().get(Integer.parseInt(id)),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        paid
                        //payment.getPayable()
                       // i.getTotal()
                );
                salesModel.saveSale(sale);
            }

            PaymentTransaction paymentTransaction = new PaymentTransaction();
           // String firstFourDigits = cardNumber.substring(0, 4);
            String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
            String maskedCardNumber =  "************" + lastFourDigits;
            paymentTransaction.setCardNumber(maskedCardNumber);
          //  paymentTransaction.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
            paymentTransaction.setExpiryDate(expiryDate);
            paymentTransaction.setCvv(cvv);
            paymentTransaction.setTotalAmount(totalAmount);

            invoiceModel.getInvoice(id);
            paymentTransaction.setOrderNumber(id);
            paymentTransaction.setPaymentStatus("SUCCESS");
            paymentTransaction.setMerchantName("DEFAULT");

            paymentTransactionModel.saveTransaction(paymentTransaction);
            //confirmController.printReceipt();
           // doneAction(event);
          //  PrintInvoice pi = new PrintInvoice(items, barcode);
            //pi.generateReport();
           // ((Node) (event.getSource())).getScene().getWindow().hide();

            ((Stage) payButton.getScene().getWindow()).close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText("Payment was successful");
            alert.setContentText("Payment was successful.");
            alert.showAndWait();

        //    LOG.info("Payment was successful!!" + "with invoice ID is: " +   invoiceModel.getInvoice(id));

            //entry for paymentDetailsPrint.fxml UI
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/paymentDetailsPrint.fxml")));
            PaymentDetailsController controller = new PaymentDetailsController();
            controller.setData(retail, items, id,payment);
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400, 500);
       //*     stage.setResizable(true);
            stage.setMaximized(false);//*
            stage.setTitle("Print Receipt");
            Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
            stage.getIcons().add(image);
            //  stage.getIcons().add(new Image("/images/tdsitelogo.png"));
            stage.setScene(scene);
            stage.show();
        }else
        {
            showAlert("Payment Failure", "Invalid card information. Please check and try again.");
        }
    }
    /**
     * shows a msg  indicating that  payment processing.
     * @param paymentTask
     */
   private void showPaymentProcessingMessage(Task<Void> paymentTask) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressBar.setPrefWidth(200);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(progressBar);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
        paymentTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                stage.close();
                showAlert("Payment Error", "An error occurred during payment processing.");
            });
        });
        paymentTask.setOnSucceeded(event -> Platform.runLater(stage::close));
    }

    /**
     * Displays a payment success message.
     * If an I/O,SQL error occurs throws sepcific exceptions
     * @throws IOException
     * @throws SQLException
     */
    private void displayPaymentSuccessMessage() throws IOException, SQLException {
        // Show payment success message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment Successful");
        alert.setHeaderText("Payment was successful");
        alert.setContentText("Payment was successful.");
        alert.showAndWait();
    }
    public static boolean isValidCardData(String cardNumber, String expiryDate, String cvv) {
        return !cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty() && isValidCreditCard(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVV(cvv);

    }
    private static boolean isValidCreditCard(String cardNumber)
    {
       // return cardNumber.matches("\\d{16}");
        // Check if the card no. is not null and is exactly 16 digits
        if (cardNumber != null && cardNumber.length() == 16)
        {
            // Check if the card number contains only numeric chars & ^\d{4}-?\d{4}-?\d{4}-?\d{4}$
            return cardNumber.matches("^\\d+$") || cardNumber.matches("^\\d{4}-?\\d{4}-?\\d{4}-?\\d{4}$");
            //return cardNumber.matches("^\\d+$");
        } else {
            return false;
        }
    }

    private static boolean isValidExpiryDate(String expiryDate) {
        // Check if the expiry date matches the pattern MM/YY
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        // Split the expiry date into month and year
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        // Validate month (from 01 to 12) & (year<=32)
        return (month >= 1 && month <= 12) && (year >= 23 && year <= 32);
    }
    private static boolean isValidCVV(String cvv)
    {
        //  return cvv.matches("\\d{3}");
        return cvv != null && cvv.matches("^[0-9]{3}$");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the "Done" action.
     * @param event
     */
    @FXML
    public void doneAction(ActionEvent event) {
        billingArea.setText("");
        PrintInvoice pi = new PrintInvoice(items, barcode,payment);
        pi.generateReport();
        LOG.info("PDF Report is generated successfully.");
        closeWindow(event);
    }
    /**
     * Handles the "Close Window" action.
     * @param event
     */
    public void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        window.hide();
    }
    /**
     * Handles the "Cancel" action.
     * @param event
     */
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
    /**
     * Handles the "Close" action.
     * @param event
     */
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Handles the "logout" action.
     * @param event
     */
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


/*     private void showPaymentProcessingMessage(Task<Void> paymentTask) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(progressBar);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();

        // duration for the payment processing is (1.2 minutes)
        Duration processingDuration = Duration.minutes(1.0);

        // Timeline to update the progress of the progress bar over the duration
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> progressBar.setProgress(0)),
                new KeyFrame(processingDuration, e -> {
                    progressBar.setProgress(1.0); // Set progress to complete
                    // Close the stage and show success message after the specified duration
                    Platform.runLater(() -> {
                        stage.close();
                        showAlert("Payment Success", "Payment processing completed successfully.");
                    });
                })
        );
        timeline.play();

        paymentTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                stage.close();
                showAlert("Payment Error", "An error occurred during payment processing.");
            });
        });
    }
*/