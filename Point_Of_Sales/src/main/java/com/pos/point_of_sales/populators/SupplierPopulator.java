package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierPopulator
{
    public Supplier populateSupplier(ResultSet resultSet) throws SQLException
    {
        Supplier supplier = new Supplier();
        supplier.setId(resultSet.getLong("id"));
        supplier.setName(resultSet.getString("name"));
        supplier.setPhone(resultSet.getString("phone"));
        supplier.setAddress(resultSet.getString("address"));
        supplier.setCreateddate(resultSet.getDate("createddate"));
        supplier.setState(resultSet.getString("state"));
        return supplier;
    }

    public PreparedStatement populateSupplierPreparedStatement(PreparedStatement preparedStatement, Supplier supplier) throws SQLException {
        java.util.Date utilDate = supplier.getCreateddate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        preparedStatement.setLong(1, supplier.getId());
        preparedStatement.setString(2, supplier.getName());
        preparedStatement.setString(3, supplier.getPhone());
        preparedStatement.setString(4, supplier.getAddress());
        preparedStatement.setTimestamp(5,sqlTimestamp);
        preparedStatement.setString(6, supplier.getState());
        return preparedStatement;
    }

    public PreparedStatement supplierUpdate(PreparedStatement preparedStatement, Supplier supplier) throws SQLException {
        java.util.Date utilDate = supplier.getCreateddate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        preparedStatement.setString(1, supplier.getName());
        preparedStatement.setString(2, supplier.getPhone());
        preparedStatement.setString(3, supplier.getAddress());
        preparedStatement.setString(4, supplier.getState());
        preparedStatement.setTimestamp(5,sqlTimestamp);
        preparedStatement.setLong(6, supplier.getId());
        return preparedStatement;
    }

    public  void deleteSupplierById(PreparedStatement preparedStatement,Supplier supplier) throws SQLException {
        preparedStatement.setLong(1, supplier.getId());
    }

}
