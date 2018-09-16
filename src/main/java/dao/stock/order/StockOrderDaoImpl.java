package dao.stock.order;

import exceptions.InvalidDeleteException;
import exceptions.InvalidUpdateException;
import models.StockOrder;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockOrderDaoImpl implements StockOrderDao {

    public List<StockOrder> getStockOrders(int start, int length, boolean received) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select STOCK_ORDER.ID, " +
                "STOCK_ORDER.BOOK_ISBN, STOCK_ORDER.QUANTITY, STOCK_ORDER.IS_RECEIVED, " +
                "BOOK.TITLE, PUBLISHER.NAME from " +
                "STOCK_ORDER inner join BOOK " +
                "on STOCK_ORDER.BOOK_ISBN = BOOK.ISBN " +
                "inner join PUBLISHER on BOOK.PUBLISHER_ID = PUBLISHER.ID " +
                "where STOCK_ORDER.IS_RECEIVED = ? LIMIT ?, ?");
        preparedStatement.setBoolean(1, received);
        preparedStatement.setLong(2, start);
        preparedStatement.setLong(3, length);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<StockOrder> stockOrders = new ArrayList<StockOrder>();
        while (resultSet.next()) {
            StockOrder stockOrder = new StockOrder();
            stockOrder.setId(resultSet.getLong(1));
            stockOrder.setISBN(resultSet.getString(2));
            stockOrder.setQuantity(resultSet.getInt(3));
            stockOrder.setReceived(resultSet.getBoolean(4));
            stockOrder.setBookTitle(resultSet.getString(5));
            stockOrder.setPublisherName(resultSet.getString(6));
            stockOrders.add(stockOrder);
        }

        preparedStatement.close();
        connection.close();

        return stockOrders;
    }

    public long getStockOrdersTotalCount(boolean received) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from STOCK_ORDER " +
                "where STOCK_ORDER.IS_RECEIVED = ?");
        preparedStatement.setBoolean(1, received);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            long count = resultSet.getLong(1);
            preparedStatement.close();
            connection.close();
            return count;
        }

        preparedStatement.close();
        connection.close();
        return 0;
    }

    @Override
    public List<StockOrder> getStockOrders(int start, int length, boolean received, String search) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select STOCK_ORDER.ID, " +
                "STOCK_ORDER.BOOK_ISBN, STOCK_ORDER.QUANTITY, STOCK_ORDER.IS_RECEIVED, " +
                "BOOK.TITLE, PUBLISHER.NAME from " +
                "STOCK_ORDER inner join BOOK " +
                "on STOCK_ORDER.BOOK_ISBN = BOOK.ISBN " +
                "inner join PUBLISHER on BOOK.PUBLISHER_ID = PUBLISHER.ID " +
                "where STOCK_ORDER.IS_RECEIVED = ? " +
                "AND (TITLE like \"%" + search +"%\" OR ISBN like \"%" + search +
                        "%\") LIMIT ?, ?");
        preparedStatement.setBoolean(1, received);
        preparedStatement.setLong(2, start);
        preparedStatement.setLong(3, length);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<StockOrder> stockOrders = new ArrayList<>();
        while (resultSet.next()) {
            StockOrder stockOrder = new StockOrder();
            stockOrder.setId(resultSet.getLong(1));
            stockOrder.setISBN(resultSet.getString(2));
            stockOrder.setQuantity(resultSet.getInt(3));
            stockOrder.setReceived(resultSet.getBoolean(4));
            stockOrder.setBookTitle(resultSet.getString(5));
            stockOrder.setPublisherName(resultSet.getString(6));
            stockOrders.add(stockOrder);
        }

        preparedStatement.close();
        connection.close();

        return stockOrders;
    }

    @Override
    public long getStockOrdersTotalCount(boolean received, String search) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from STOCK_ORDER" +
                " INNER JOIN BOOK ON BOOK.ISBN = STOCK_ORDER.BOOK_ISBN " +
                "WHERE STOCK_ORDER.IS_RECEIVED = ? AND (STOCK_ORDER.BOOK_ISBN LIKE \"%" +
                search + "%\" OR BOOK.TITLE LIKE \"%" + search + "%\")");
        preparedStatement.setBoolean(1, received);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            long count = resultSet.getLong(1);
            preparedStatement.close();
            connection.close();
            return count;
        }

        preparedStatement.close();
        connection.close();
        return 0;
    }

    public void receiveStockOrder(long stockOrderId) throws SQLException, ClassNotFoundException, InvalidUpdateException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update STOCK_ORDER SET IS_RECEIVED = true " +
                "where STOCK_ORDER.ID = ?");
        preparedStatement.setLong(1, stockOrderId);
        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (affectedRows == 0) {
            throw new InvalidUpdateException("Invalid Stock Order ID");
        }
    }

    public void deleteStockOrder(long stockOrderId) throws SQLException, ClassNotFoundException, InvalidDeleteException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from STOCK_ORDER " +
                "where STOCK_ORDER.ID = ?");
        preparedStatement.setLong(1, stockOrderId);
        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        if (affectedRows == 0) {
            throw new InvalidDeleteException("Invalid Stock Order ID");
        }
    }

    public int checkOrderNotReceived(long stockOrderId) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from STOCK_ORDER " +
                "where STOCK_ORDER.ID = ? and STOCK_ORDER.IS_RECEIVED = false");
        preparedStatement.setLong(1, stockOrderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.close();
            connection.close();
            return 1;
        }
        preparedStatement.close();
        connection.close();
        return 0;
    }

    public int checkOrderExistence(long stockOrderId) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from STOCK_ORDER " +
                "where STOCK_ORDER.ID = ?");
        preparedStatement.setLong(1, stockOrderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.close();
            connection.close();
            return 1;
        }
        preparedStatement.close();
        connection.close();
        return 0;
    }

    public void addStockOrder(StockOrder stockOrder) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "STOCK_ORDER(BOOK_ISBN, QUANTITY) VALUES (?, ?)");
        preparedStatement.setString(1, stockOrder.getISBN());
        preparedStatement.setInt(2, stockOrder.getQuantity());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}
