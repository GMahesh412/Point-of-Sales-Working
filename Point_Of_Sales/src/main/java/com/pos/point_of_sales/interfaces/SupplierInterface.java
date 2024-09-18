package com.pos.point_of_sales.interfaces;

import com.pos.point_of_sales.entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents an interface for managing suppliers in the point of sales system.
 * @author Mahesh Yadav
 */
public interface SupplierInterface {
    /**
     * The list of suppliers.
     */
    public ObservableList<Supplier> SUPPLIERLIST = FXCollections.observableArrayList();
}
