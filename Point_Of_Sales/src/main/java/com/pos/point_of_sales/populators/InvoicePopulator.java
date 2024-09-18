package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.Employee;
import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.model.EmployeeModel;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class InvoicePopulator
{
    private Invoice invoice;
    private EmployeeModel employeeModel;

    public Invoice populateInvoice(ResultSet resultSet) throws SQLException
    {
        invoice = new Invoice();
        employeeModel = new EmployeeModel();
        invoice.setId(resultSet.getString("id"));
        Long employeeId = resultSet.getLong("employeeId");
        Employee employee = employeeModel.getEmployee(employeeId);
        if (Objects.nonNull(employee))
        {
            invoice.setEmployee(employee);
        }
        invoice.setTotal(resultSet.getLong("total"));
        invoice.setSgst(resultSet.getLong("sgst"));
        invoice.setDiscount(resultSet.getLong("discount"));
        invoice.setPayable(resultSet.getLong("payable"));
        invoice.setPaid(resultSet.getDouble("paid"));
        invoice.setReturned(resultSet.getLong("returned"));
        invoice.setDatetime(Date.valueOf(resultSet.getDate("datetime").toString()));
        //  invoice.setDatetime(resultSet.getDate("datetime"));
        invoice.setCgst(resultSet.getLong("cgst"));
        return invoice;
    }

    public PreparedStatement populatePreparedStatement(PreparedStatement preparedStatement, Invoice invoice) throws SQLException {

        java.util.Date utilDate = invoice.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

        preparedStatement.setString(1, invoice.getId());
        preparedStatement.setLong(2, invoice.getEmployee().getId());
        preparedStatement.setLong(3, (long) invoice.getTotal());
        preparedStatement.setLong(4, (long) invoice.getSgst());
        preparedStatement.setLong(5, (long) invoice.getDiscount());
        preparedStatement.setLong(6, (long) invoice.getPayable());
        preparedStatement.setDouble(7, invoice.getPaid());
        preparedStatement.setLong(8, (long) invoice.getReturned());
      //  preparedStatement.setDate(9, new Date(System.currentTimeMillis()));
        preparedStatement.setTimestamp(9,sqlTimestamp);
        preparedStatement.setLong(10, (long) invoice.getCgst());
        return preparedStatement;
    }

    public  void populatePreparedStatementForDelete(PreparedStatement preparedStatement, Invoice invoice) throws SQLException {
        preparedStatement.setString(1, invoice.getId());
    }

  /*  public  void populateForGetInvoiceId(PreparedStatement preparedStatement, long id) throws SQLException {
        preparedStatement.setString(1, String.valueOf(id));

    }
*/
  public  void populateForGetInvoiceId(PreparedStatement preparedStatement, String id) throws SQLException {
      preparedStatement.setString(1,id);

  }

    public void populatePreparedStatementForSave(PreparedStatement preparedStatement, Invoice invoice) throws SQLException {
        java.util.Date utilDate = invoice.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

      preparedStatement.setString(1, invoice.getId());
        preparedStatement.setLong(2, invoice.getEmployee().getId());
        preparedStatement.setLong(3, (long) invoice.getTotal());
        preparedStatement.setLong(4, (long) invoice.getSgst());
        preparedStatement.setLong(5, (long) invoice.getDiscount());
        preparedStatement.setLong(6, (long) invoice.getPayable());
        preparedStatement.setDouble(7, invoice.getPaid());
        preparedStatement.setLong(8, (long) invoice.getReturned());
        //preparedStatement.setDate(9, new Date(System.currentTimeMillis()));
        preparedStatement.setTimestamp(9,sqlTimestamp);

        preparedStatement.setLong(10, (long) invoice.getCgst());
    }
}
