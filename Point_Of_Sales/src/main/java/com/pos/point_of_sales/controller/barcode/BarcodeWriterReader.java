package com.pos.point_of_sales.controller.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.*;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;


/**
 * This class is never used, it is just written to test the Barcode reading/scanning, & generating barcode and barcodeImage .
 * @author Mahesh Yadav
 * */
public class BarcodeWriterReader {
    public static void main(String[] args) {
        BarcodeWriterReader barcodeReader = new BarcodeWriterReader();
    // barcodeReader.generateBarcode();
     barcodeReader.readBarcode();
    }


    public void generateBarcode() {
        try {
            String text = "https://techdenali.com/";
            String path = "D:/Barcodes/barcode_Code93.jpg";
           // EAN13Writer writer = new EAN13Writer(); //not generating
            Code93Writer writer = new Code93Writer();
           //  Code128Writer writer = new Code128Writer();
            //Code39Writer writer = new Code39Writer();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.CODE_93, 200, 50);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

            System.out.println("Barcode generated");
        } catch (Exception e) {
            System.out.println("Error while creating barcode..");
        }
    }
    public void readBarcode() {

        try {
          //  String path = "D:/Barcodes/barcode_Code93.jpg";
            String path = "D:\\POS_jdbc\\Point_Of_Sales\\src\\main\\resources\\Barcodes\\barcode_890000000058.jpg";
            BufferedImage bf = ImageIO.read(new FileInputStream(path));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);
            System.out.println(result.getText());
        System.out.println("Barcode reading success");
        } catch (Exception e) {
            System.out.println("Error while creating barcode..");
        }
    }
}