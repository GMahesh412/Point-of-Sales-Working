package com.pos.point_of_sales.controller.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.model.ProductModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;

/**
 * The BarcodeGenerator class is responsible for generating barcodes for Products.
 * @author Mahesh_Yadav
 */
public class BarcodeGenerator {
    private ProductModel productModel;
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeGenerator.class);

    public static void main(String[] args) {
        new BarcodeGenerator().barCode();
    }

    /**
     * Generates barcodes for products and updates the db with generated barcodes.
     *
     * @return null
     */
    public String barCode() {
        try {
            Connection connection = JDBCConnectionFactory.getConnection();
            productModel = new ProductModel();
            List<Product> products = productModel.getProducts();
            for (Product product : products) {
                // Check if the product already has a barcode
                if (product.getBarcode() == null || product.getBarcode().isEmpty()) {
                    String productId = String.valueOf(product.getId());
                    String manufacturerCode = generateManufacturerCode(); // Generate 5-digit manufacturer code
                    String paddedProductId = padLeft(productId, 12 - manufacturerCode.length(), '0');
                    String barcodeNumber = generateBarcode(manufacturerCode + paddedProductId);
                    updateBarcodeInDatabase(connection, barcodeNumber, Long.valueOf(productId));
                    LOG.info("Generated barcode for product ID {}: {}", productId, barcodeNumber);
                }
            }
        } catch (SQLException | RuntimeException e) {
            handleException(e);
        }
        return null;
    }

    /**
     * Generates a barcode image for the given barcode content.
     *
     * @param barcodeContent The content of the barcode
     * @return  barcodeContent
     */
    private String generateBarcode(String barcodeContent) {
        // Indian EAN-13 prefix: 890 (for GS1 India)
        String gs1Prefix = "890";
        barcodeContent = gs1Prefix + barcodeContent.substring(3); // Replacing first three digits with GS1 prefix
        // Generate the check digit & Append the check digit to the barcode content
        int checkDigit = generateCheckDigit(barcodeContent);
        barcodeContent = barcodeContent + checkDigit;

        BarcodeFormat barcodeFormat = BarcodeFormat.EAN_13;
        int width = 200;
        int height = 50;
        try {
            EAN13Writer barcodeWriter = new EAN13Writer();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 10);
            BitMatrix bitMatrix = barcodeWriter.encode(barcodeContent, barcodeFormat, width, height, hints);
            String filePath = "D:/POS_jdbc/Point_Of_Sales/src/main/resources/Barcodes/barcode_" + barcodeContent + ".jpg";
            File file = new File(filePath);
            MatrixToImageWriter.writeToStream(bitMatrix, "JPG", new FileOutputStream(file));
            return barcodeContent;
        } catch (IOException e) {
            throw new RuntimeException("Error generating barcode: " + e.getMessage(), e);
        }
    }

    /**
     * Generates the check digit for the given barcode content.
     *
     * @param barcodeContent The content of the barcode
     * @return The check digit
     */
    private int generateCheckDigit(String barcodeContent) {
        // Calculate the check digit for EAN-13 barcode
        int sum = 0;
        for (int i = barcodeContent.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(barcodeContent.charAt(i));
            sum += (i % 2 == 0) ? digit * 1 : digit * 3;
        }
        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }

    /**
     * Generates the manufacturer code.
     *
     * @return The manufacturer code
     */
    private String generateManufacturerCode() {
        return "50005";
    }

    /**
     * Updates the barcode in the database for the given product ID.
     *
     * @param conn    The database connection
     * @param barcode The barcode to update
     * @param id      The product ID
     */
    private static void updateBarcodeInDatabase(Connection conn, String barcode, Long id) {
        String sql = "UPDATE Products SET barcode = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating barcode in database: " + e.getMessage(), e);
        }
    }

    /**
     * Handles exceptions by logging error messages.
     *
     * @param e The exception
     */
    private static void handleException(Exception e) {
        LOG.error("An error occurred: {}", e.getMessage());
        handleSQLException((SQLException) e);
    }

    /**
     * Pads the given string with the specified length and character.
     *
     * @param str     The string to pad
     * @param length  The desired length
     * @param padChar The padding character
     * @return The padded string
     */
    private static String padLeft(String str, int length, char padChar) {
        StringBuilder paddedString = new StringBuilder(str);
        while (paddedString.length() < length) {
            paddedString.insert(0, padChar);
        }
        return paddedString.toString();
    }
}
