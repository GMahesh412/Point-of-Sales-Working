package com.pos.point_of_sales.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.Payment;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * PrintInvoice Utility class is to generate PDF invoices.
 * this class is used by PaymentTransactionController,confirmController to generate Report after payment.
 * @author Mahesh_Yadav
 */
public class PrintInvoice {
    private static final Logger LOG = LoggerFactory.getLogger(PrintInvoice.class);

    private final ObservableList<Item> items;
    private final String barcode;
    private final Payment payment;

    /**
     * Constructor to initialize PrintInvoice with items, barcode, and payment details.
     *
     * @param items    The list of items in the invoice.
     * @param barcode  The barcode associated with the invoice.
     * @param payment  The payment details associated with the invoice.
     */
    public PrintInvoice(ObservableList<Item> items, String barcode, Payment payment) {
        this.items = items;
        this.barcode = barcode;
        this.payment = payment;
    }

    /**
     * Generates the PDF report for the invoice.
     */
    public void generateReport() {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
            document.open();

            addHeader(document, writer);
            addTable(document);
            addFooter(document);

            document.close();
            LOG.debug(String.valueOf(document));
        } catch (DocumentException | IOException ex) {
            LOG.error("Error occurred while generating PDF invoice: ", ex);
        }
    }

    private void addHeader(Document document, PdfWriter writer) throws DocumentException {
        try {
            Paragraph header = new Paragraph("Invoice Report", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            String currentTime = "\t\t Date: " + (new Date(System.currentTimeMillis())) + "\n";
            Paragraph dateParagraph = new Paragraph(currentTime, new Font(Font.FontFamily.HELVETICA, 12));
            dateParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(dateParagraph);

            document.add(new Paragraph("\n"));

            Paragraph barcodeParagraph = new Paragraph("Receipt ID", new Font(Font.FontFamily.HELVETICA, 12));
            barcodeParagraph.setAlignment(Element.ALIGN_LEFT);

            document.add(barcodeParagraph);
            addBarcode(document, writer);
            document.add(new Paragraph("\n"));
        } catch (Exception ex) {
            LOG.error("Error occurred while adding header to PDF invoice: ", ex);
        }
    }

    private void addTable(Document document) throws DocumentException {
        try {
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            addTableHeader(table);
            addTableData(table);
            document.add(table);
        } catch (Exception ex) {
            LOG.error("Error occurred while adding table to PDF invoice: ", ex);
        }
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"Item", "Price", "Quantity", "SGST", "CGST", "Discount", "Total"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addTableData(PdfPTable table) {
        for (Item item : items) {
            table.addCell(item.getItemName());
            table.addCell(String.valueOf(item.getUnitPrice()));
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(payment.getSgst()));
            table.addCell(String.valueOf(payment.getCgst()));
            table.addCell(String.valueOf(payment.getDiscount()));
            table.addCell(String.valueOf(payment.getPayable()));
        }
    }

    private void addFooter(Document document) throws DocumentException {
        try {
            Paragraph footer = new Paragraph("Thank you for your purchase.", new Font(Font.FontFamily.HELVETICA, 12));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
        } catch (Exception ex) {
            LOG.error("Error occurred while adding footer to PDF invoice: ", ex);
        }
    }

    private void addBarcode(Document document, PdfWriter writer) throws DocumentException {
        try {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            BarcodeEAN codeEAN = new BarcodeEAN();
            codeEAN.setCodeType(Barcode.EAN13);
            codeEAN.setCode(barcode);
            Image image = codeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.DARK_GRAY);
            paragraph.add(image);
            document.add(paragraph);
        } catch (Exception ex) {
            LOG.error("Error occurred while adding barcode to PDF invoice: ", ex);
        }
    }
}
