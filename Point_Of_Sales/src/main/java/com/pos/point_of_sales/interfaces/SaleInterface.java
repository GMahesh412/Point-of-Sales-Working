package com.pos.point_of_sales.interfaces;


import com.pos.point_of_sales.entity.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents an interface for managing sales in the point of sales system.
 * @author Mahesh Yadav
 */
public interface SaleInterface {

    /**
     * The list of sales.
     */
    public ObservableList<Sale> SALELIST = FXCollections.observableArrayList();
}
