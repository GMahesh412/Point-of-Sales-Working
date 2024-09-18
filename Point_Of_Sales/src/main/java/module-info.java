module com.pos.point_of_sales {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.apache.commons.codec;

    // requires org.slf4j;
    requires poi.ooxml;
    requires poi;
    requires AnimateFX;
    requires slf4j.api;
    requires com.jfoenix;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires itextpdf;
    requires org.apache.logging.log4j;
    requires com.opencsv;


    opens  com.pos.point_of_sales.pdf to javafx.fxml;
    exports com.pos.point_of_sales.pdf to javafx.graphics;
/*
    exports com.pos.point_of_sales.pdf to javafx.fxml;
*/


    opens com.pos.point_of_sales.controller.product to  javafx.fxml;
    exports com.pos.point_of_sales.controller.product to  javafx.fxml;

    opens com.pos.point_of_sales.controller.report to  javafx.fxml;
    exports com.pos.point_of_sales.controller.report to  javafx.fxml;

    opens com.pos.point_of_sales.controller.pos to  javafx.fxml;
    exports com.pos.point_of_sales.controller.pos to  javafx.fxml;

    opens com.pos.point_of_sales.controller.employee to  javafx.fxml;
    exports com.pos.point_of_sales.controller.employee to  javafx.fxml;

    opens com.pos.point_of_sales.controller.supplier to  javafx.fxml;
    exports com.pos.point_of_sales.controller.supplier to  javafx.fxml;

    opens com.pos.point_of_sales.controller.purchase to  javafx.fxml;
    exports com.pos.point_of_sales.controller.purchase to  javafx.fxml;

    opens com.pos.point_of_sales.controller.sales to  javafx.fxml;
    exports com.pos.point_of_sales.controller.sales to  javafx.fxml;

    opens com.pos.point_of_sales.controller.category to  javafx.fxml;
    exports com.pos.point_of_sales.controller.category to  javafx.fxml;

    opens com.pos.point_of_sales.entity to  javafx.fxml,javafx.base;
    exports com.pos.point_of_sales.entity to  javafx.fxml,javafx.base;


    opens com.pos.point_of_sales to javafx.fxml;
    opens com.pos.point_of_sales.controller.login to  javafx.fxml;
    exports com.pos.point_of_sales;
    exports com.pos.point_of_sales.controller.admin to  javafx.fxml;
    exports com.pos.point_of_sales.controller.login;

    opens com.pos.point_of_sales.controller.admin to  javafx.fxml;
    //opens com.pos.point_of_sales.controller.card to java.base;
    opens com.pos.point_of_sales.controller.card to javafx.fxml;
    exports com.pos.point_of_sales.controller.card to  javafx.fxml;

    opens com.pos.point_of_sales.controller.payment to javafx.fxml;
    exports  com.pos.point_of_sales.controller.payment to  javafx.fxml;

    opens com.pos.point_of_sales.controller.barcode to javafx.fxml;
    exports com.pos.point_of_sales.controller.barcode to  javafx.fxml;

    // opens com.pos.point_of_sales.controller.barcode to  com.pos.point_of_sales.controller.pos;
    // exports com.pos.point_of_sales.controller.pos to  com.pos.point_of_sales.controller.barcode;



}