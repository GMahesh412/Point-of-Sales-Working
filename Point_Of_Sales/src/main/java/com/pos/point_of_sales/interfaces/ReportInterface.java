package com.pos.point_of_sales.interfaces;

import com.pos.point_of_sales.entity.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents an interface for managing reports in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface ReportInterface {

    /** The list of reports. */
    public ObservableList<Invoice> REPORTLIST = FXCollections.observableArrayList();
}