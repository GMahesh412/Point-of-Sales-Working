package com.pos.point_of_sales.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.PaymentTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Receipt Utility class to generate PDF receipts.
 */
public class Receipt {
    private static final Logger LOG = LoggerFactory.getLogger(Receipt.class);

    private final ObservableList<Item> items;
    private final ObservableList<PaymentTransaction> paymentTransactions;
    private final String barcode;

    /**
     * Constructor to initialize Receipt with items, payment transactions, and barcode.
     *
     * @param items               The list of items in the receipt.
     * @param paymentTransactions The list of payment transactions associated with the receipt.
     * @param barcode             The barcode associated with the receipt.
     */
    public Receipt(ObservableList<Item> items, ObservableList<PaymentTransaction> paymentTransactions, String barcode) {
        this.items = FXCollections.observableArrayList(items);
        this.paymentTransactions = paymentTransactions;
        this.barcode = barcode;
    }

    /**
     * Generates the PDF report for the receipt.
     */
    public void generateReport() {
        try {
            Document document = new Document();
            FileOutputStream fs = new FileOutputStream("Report.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fs);
            document.open();

            Paragraph paragraph = new Paragraph("Receipt ID");
            document.add(paragraph);
            addEmptyLine(paragraph, 5);

            PdfContentByte cb = writer.getDirectContent();
            BarcodeEAN codeEAN = new BarcodeEAN();
            codeEAN.setCodeType(codeEAN.EAN13);
            codeEAN.setCode(barcode);
            document.add(codeEAN.createImageWithBarcode(cb, BaseColor.BLACK, BaseColor.DARK_GRAY));
            addEmptyLine(paragraph, 5);

            PdfPTable table = createTable();
            document.add(table);

            document.close();
            LOG.info("PDF receipt generated successfully.");
        } catch (DocumentException | FileNotFoundException ex) {
            LOG.error("Error occurred while generating PDF receipt: {}", ex.getMessage());
        }
    }

    private PdfPTable createTable() {
        PdfPTable table = new PdfPTable(7);
        PdfPCell c1 = new PdfPCell(new Phrase("Item"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("SGST"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CGST"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Discount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (Item i : items) {
            table.addCell(i.getItemName());
            table.addCell(String.valueOf(i.getUnitPrice()));
            table.addCell(String.valueOf(i.getQuantity()));
            table.addCell(String.valueOf(i.getSgst()));
            table.addCell(String.valueOf(i.getCgst()));
            table.addCell(String.valueOf(i.getDiscount()));
            table.addCell(String.valueOf(i.getTotal()));
        }

        return table;
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}


/*package com.pos.point_of_sales.pdf;


import java.io.OutputStream;

public class Receipt {

    public static void main(String[] args) throws Exception
    {
        String portName = "/dev/ttyS4";
        Integer baudrate = 57600;
        Integer timeout = 1000;

        SerialPort serialPort = (SerialPort)CommPortIdentifier.getPortIdentifier(portName).open(Receipt.class.getName(), 1000);
        serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        serialPort.enableReceiveTimeout(timeout);

        try(OutputStream os = serialPort.getOutputStream()) {
            // select double width and height font
            os.write(new byte[] {0x1b, 0x21, 0x31});

            os.write("       AROMA CAFE\n".getBytes());
            os.write("   1211 Green Street\n".getBytes());
            os.write("      New York, NY\n".getBytes());

            // select normal font
            os.write(new byte[] {0x1b, 0x21, 0x01});

            os.write("03-12-2016       1:11PM\n".getBytes());
            os.write("TBL 1            HOST ALISON\n".getBytes());
            os.write("VISA ######8281\n".getBytes());
            os.write("\n".getBytes());
            os.write("QTY  DESC                              AMT\n".getBytes());
            os.write("----------------------------------------------\n".getBytes());
            os.write("1   GINGER CARROT SOUP                   $6.79\n".getBytes());
            os.write("1   HOUSE SALAD                          $7.69\n".getBytes());
            os.write("1   SURF AND RUTF - 1 PERS              $48.79\n".getBytes());
            os.write("1   WINE - GLASS - FIXE                 $11.50\n".getBytes());
            os.write("1   CHOC CAKE                            $6.75\n".getBytes());
            os.write("\n".getBytes());

            // select double width and height font
            os.write(new byte[] {0x1b, 0x21, 0x31});
            os.write("    AMOUNT    $90.52\n".getBytes());

            os.write(new byte[] {0x1b, 0x21, 0x01});
            os.write("\n".getBytes());
            os.write("        SUB-TOTAL           $81.52\n".getBytes());
            os.write("        TAX                  $9.00\n".getBytes());
            os.write("        BALANCE             $90.52\n".getBytes());
            os.write("\n".getBytes());
            os.write("\n".getBytes());
            os.write("\n".getBytes());

            // center text
            os.write(new byte[] {0x1b, 0x61, 0x31});

            // set barcode height to 80px
            os.write(new byte[] {0x1d, 0x68, 0x50});

            // print CODE39 with text TEST
            os.write(new byte[] {0x1d, 0x6b, 0x45, 0x04, 'T', 'E', 'S', 'T'});
            os.flush();
        }
    }
}
*/