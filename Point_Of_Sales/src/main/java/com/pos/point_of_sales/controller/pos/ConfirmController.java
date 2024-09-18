package com.pos.point_of_sales.controller.pos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pos.point_of_sales.controller.payment.PaymentTransactionController;
import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.Payment;
import com.pos.point_of_sales.entity.PaymentTransaction;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * ConfirmController class for handling confirmation actions and UI interactions.
 * It generates billing context, prints, and downloads receipts.
 * @author Mahesh Yadav
 */
public class ConfirmController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmController.class);

    @FXML
    public TextArea billingArea;
    @FXML
    private Label retailLabel;

    private double retail;
    private ObservableList<Item> items;
    private String barcode;
    private Payment payment;
    private  Item item;
    private PaymentTransactionController paymentTransactionController;
    private PaymentTransaction paymentTransaction;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    @FXML
    private ImageView myImageView ;
    private PaymentTransactionModel  paymentTransactionModel;
    @FXML
    private Text billingText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paymentTransaction = new PaymentTransaction();
        paymentTransactionController = new PaymentTransactionController();
        paymentTransactionModel = new PaymentTransactionModel();
        salesModel = new SalesModel();
        invoiceModel = new InvoiceModel();
        generateBillingContext();

    }

    /**
     * Generates billing context with invoice details, for the UI.
     */
    private void generateBillingContext() {
        String title = "\t\t\t\t\t INVOICE\t\t\t\t\t\n";
        String name = "\t\t\t\t\tTD SuperMart\t\t\t\t\t\n";
        String address = "\t\tAddress: 8-2-269/A/2 1-6, Road No.2,\n\t\tPark View Enclave, Banjara Hills, \n\t\tHyderabad, Telangana 500033\n";
        String currentTime = "\t\t Date: " + String.valueOf(new Date(System.currentTimeMillis())) + "\n";
        String id = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        String invoiceId = "\t\t InvoiceId: " + barcode + "\n";
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedRetail = df.format(retail);
        retailLabel.setText("Change: INR (₹) " + roundedRetail);
        double total = 0.0;
        double discount = 0.0;
        double cgst = 0.0;
        double sgst = 0.0;
        String lines = "_______________________________________________________________________________\n";
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
        String visit = "\t\t\tThanks For Shopping Visit Again!\t\t\t\t\t";
        totalprice.append(Math.round(total)).append("\n\n").append(lines).append("\n").append(visit);
        discountdetail.append(discount).append("\n");
        cgstDetail.append(Math.round(cgst)).append("\n");
        sgstDetail.append(Math.round(sgst)).append("\n");
        String billingTexts = ( title + name + address + currentTime + invoiceId + details.toString() + sgstDetail.toString() + cgstDetail.toString()
                + discountdetail.toString() + totalprice.toString());
        billingText.setText(billingTexts);
    }

    /**
     *  setData- Sets data for confirmation.
     * @param retail The retail amount
     * @param items The list of items
     * @param barcode The barcode
     * @param payment The payment details
     */
    public void setData(double retail, ObservableList<Item> items, String barcode,Payment payment) {
        this.retail = retail;
        this.items = FXCollections.observableArrayList(items);
        this.barcode = barcode;
        this.payment = payment;
    }

    /**
     * doneAction - Handles the action event for finalizing the transaction.
     * @param event The action event
     */
    @FXML
    public void doneAction(ActionEvent event) {
        billingText.setText("");
        PrintInvoice pi = new PrintInvoice(items, barcode,payment);
        pi.generateReport();
        //generatePDF();
        LOG.info("PDF Report is generated successfully.");
        ((Node) (event.getSource())).getScene().getWindow().hide();
        //  closeWindow(event);
    }

    /**
     *  printAction - Handles the action event for printing the receipt.
     * @param event The action event
     */
    @FXML
    public void printAction(ActionEvent event) {
        printReceipt();
    }
    /**
     * Prints the receipt.
     */
    @FXML
    public void printReceipt() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean success = printerJob.printPage(billingText.lookup("Text"));
            if (success) {
                printerJob.endJob();
            }
        }
    }


    /**
     * Downloads the receipt as a PDF.
     */
    @FXML
    public void downloadReceipt() {
        generatePDF();
    }

    /**
     *  closeAction -Closes the current window. when it triggered.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event) {
        closeWindow(event);
    }

    /**
     * generates a PDF document for the receipt.
     */
    private void generatePDF() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("receipt.pdf"));
            document.open();
            document.add(new Paragraph(billingText.getText()));
            document.close();
        } catch (DocumentException | FileNotFoundException e)
        {
            LOG.error("Error generating PDF receipt: {}", e.getMessage());
        }
    }

    /**
     * Closes the current window.
     * @param event The action event
     */
    public void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();
        window.hide();
    }

}
