package com.pos.point_of_sales.interfaces;

import com.pos.point_of_sales.entity.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Represents an interface for managing purchases in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface PurchaseInterface {

    /** The list of purchases. */
    public ObservableList<Purchase> PURCHASELIST = FXCollections.observableArrayList();
}