package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.*;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.InvoiceModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SalesPopulator
{
    private ProductModel productModel;
    private InvoiceModel invoiceModel;

    public Sale populateSale(ResultSet resultSet) throws SQLException
    {
        Sale sale = new Sale();
        productModel = new ProductModel();
        invoiceModel = new InvoiceModel();
        sale.setId(resultSet.getLong("id"));
        Long invoiceId = resultSet.getLong("invoiceId");
        Invoice invoice = invoiceModel.getInvoice(String.valueOf(invoiceId));
        if (Objects.nonNull(invoice)){
            sale.setInvoice(invoice);
        }
        Long productId = resultSet.getLong("productId");
        Product product = productModel.getProduct(productId);
        if (Objects.nonNull(product)){
            sale.setProduct(product);
        }
        sale.setQuantity(resultSet.getDouble("quantity"));
        sale.setPrice(resultSet.getDouble("price"));
        sale.setTotal(resultSet.getDouble("total"));
       // sale.setDatetime(resultSet.getDate("datetime"));
        sale.setDatetime(Date.valueOf(resultSet.getDate("datetime").toString()));
        return sale;
    }

    public PreparedStatement populateSalePreparedStatement(PreparedStatement preparedStatement, Sale sale) throws SQLException {
       /* java.util.Date utilDate = sale.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());*/
        preparedStatement.setLong(1, sale.getId());
        preparedStatement.setString(2,sale.getInvoice().getId());
        preparedStatement.setLong(3, sale.getProduct().getId());
        preparedStatement.setInt(4, (int) sale.getQuantity());
        preparedStatement.setDouble(5, sale.getPrice());
        preparedStatement.setDouble(6, sale.getTotal());
        //preparedStatement.setTimestamp(7,sqlTimestamp);
        preparedStatement.setDate(7, sale.getDatetime());
        return preparedStatement;
    }
    public PreparedStatement saveSale(PreparedStatement preparedStatement, Sale sale) throws SQLException {
       /* java.util.Date utilDate = sale.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());*/
        //preparedStatement.setLong(1, sale.getId());
        preparedStatement.setString(1,sale.getInvoice().getId());
        preparedStatement.setLong(2, sale.getProduct().getId());
        preparedStatement.setInt(3, (int) sale.getQuantity());
        preparedStatement.setDouble(4, sale.getPrice());
        preparedStatement.setDouble(5, sale.getTotal());
        preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
       // preparedStatement.setTimestamp(6,sqlTimestamp);
        return preparedStatement;
    }



}
