package com.pos.point_of_sales.controller.payment;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.pos.point_of_sales.entity.*;
import com.pos.point_of_sales.model.InvoiceModel;
import com.pos.point_of_sales.model.PaymentTransactionModel;
import com.pos.point_of_sales.model.SalesModel;
import com.pos.point_of_sales.pdf.PrintInvoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * PaymentDetailsController class for managing payment details and receipt/Print Invoice.
 * @author Mahesh Yadav
 */
public class PaymentDetailsController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentDetailsController.class);

    @FXML
    private Text billingText;
    private String barcode;
    private double retail;
    private ObservableList<Item> items;
    private PaymentTransactionController paymentTransactionController;
    private Payment payment;
    private PaymentTransaction paymentTransaction;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    private PaymentTransactionModel  paymentTransactionModel;


    public void initialize(URL location, ResourceBundle resources) {
        paymentTransaction = new PaymentTransaction();
        paymentTransactionController = new PaymentTransactionController();
        paymentTransactionModel = new PaymentTransactionModel();
        salesModel = new SalesModel();
        invoiceModel = new InvoiceModel();
        generateBillingContext();

    }
    /**
     * Generates the billing context for the payment details after Payment we'll get Print Invoice.
     */
       private void generateBillingContext() {
        DecimalFormat decfor = new DecimalFormat("0.00");
        String title = "\t\t\t\t\t INVOICE\t\t\t\t\t\n";
        Chunk invoiceTitle = new Chunk("Invoice\n");
        invoiceTitle.setFont(FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
        String name = "\t\t\t\t\tTD SuperMart\t\t\t\t\t\n";
        String address = "\t\tAddress: 8-2-269/A/2 1-6, Road No.2,\n\t\tPark View Enclave, Banjara Hills, \n\t\tHyderabad, Telangana 500033\n";
        String currentTime = "\t\t Date: " + String.valueOf(new Date(System.currentTimeMillis())) + "\n";
        String id = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        String invoiceId = "\t\t InvoiceId: " + barcode + "\n";
        double total = 0.0;
        double discount = 0.0;
        double cgst = 0.0;
        double sgst = 0.0;
        String lines = "_____________________________________________________________________________\n";
        StringBuilder details = new StringBuilder("\nItem Name\t\t\t" + "Cost\t\t\t" + "Quantity\t\t\t" + "Price\n");
        details.append(lines);
        Invoice invoice;
        for (Item i : items) {
            details.append(i.getItemName())
                    .append("\t\t\t")
                    .append(i.getUnitPrice())
                    .append("\t\t\t")
                    .append(i.getQuantity())
                    .append("\t\t\t")
                    .append(Math.round(i.getTotal()))
                    .append("\n");
            total =  total + i.getTotal();
        }
        cgst = cgst + payment.getCgst();
        sgst = sgst + payment.getSgst();
        discount = payment.getDiscount();
        total = total + cgst + sgst;
        total = total - discount;

        details.append(lines);
        StringBuilder totalprice = new StringBuilder("\t\t\t\t\t\t\t\t\t\tTotal\t");
        StringBuilder discountdetail = new StringBuilder("\t\t\t\t\t\t\t\t\t\tDiscount\t");
        StringBuilder cgstDetail = new StringBuilder("\t\t\t\t\t\t\t\t\t\tCGST\t");
        StringBuilder sgstDetail = new StringBuilder("\t\t\t\t\t\t\t\t\t\tSGST\t");
        String visit = "\t\tThanks For Shopping TD SuperMart. Visit Again!!\t\t\t\t\t";
        totalprice.append(Math.ceil(total)).append("\n\n").append(lines).append("\n").append(visit);
        discountdetail.append(discount).append("\n");
        cgstDetail.append(decfor.format(cgst)).append("\n");
        sgstDetail.append(decfor.format(sgst)).append("\n");
        String billingTexts = ( title + lines+ name + address + currentTime + invoiceId  + details.toString() + sgstDetail.toString() + cgstDetail.toString()
                + discountdetail.toString() + totalprice.toString()+lines);
        billingText.setText(billingTexts);
    }

/*    @FXML
    private void printAction(ActionEvent event) {
        printReceipt();
    }
    @FXML
    public void printReceipt() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean success = printerJob.printPage(billingText.lookup("Text"));
            if (success) {
                printerJob.endJob();
            }
        }
    }*/



    /**
     * prints the billing details.
     * @param event The action event
     */
    @FXML
    public void printAction(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(billingText.getScene().getWindow())) {
            boolean success = printerJob.printPage(billingText.lookup("Text"));
            if (success) {
                printerJob.endJob();
            }
        }
    }

    /**
     * closes the payment details window.
     * @param event The action event
     */
    @FXML
    private void doneAction(ActionEvent event) {
        Item item = new Item();
        Stage stage = (Stage) billingText.getScene().getWindow();
        billingText.setText("");
        PrintInvoice pi = new PrintInvoice(items, barcode,payment);
        pi.generateReport();
        //printReceipt();
        stage.close();
        LOG.info("Payment was successful!!" + "with invoice ID is: " +  barcode + "Items : " + items  );

    }
    /**
     * Sets the data for payment details.
     * @param retail , retail value
     * @param items The list of items
     * @param barcode The barcode
     * @param payment The payment details
     */
    public void setData(double retail, ObservableList<Item> items, String barcode, Payment payment) {
        this.retail = retail;
        this.items = FXCollections.observableArrayList(items);
        this.barcode = barcode;
        this.payment = payment;
    }

    /**
     * Closes the payment details window.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
