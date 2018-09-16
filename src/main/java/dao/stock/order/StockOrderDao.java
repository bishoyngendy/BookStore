package dao.stock.order;

import exceptions.InvalidDeleteException;
import exceptions.InvalidUpdateException;
import models.StockOrder;

import java.sql.SQLException;
import java.util.List;

public interface StockOrderDao {
    List<StockOrder> getStockOrders(int start, int length, boolean received) throws SQLException, ClassNotFoundException;
    long getStockOrdersTotalCount(boolean received) throws SQLException, ClassNotFoundException;

    List<StockOrder> getStockOrders(int start, int length, boolean received, String search) throws SQLException, ClassNotFoundException;
    long getStockOrdersTotalCount(boolean received, String search) throws SQLException, ClassNotFoundException;

    void receiveStockOrder(long stockOrderId) throws SQLException, ClassNotFoundException, InvalidUpdateException;
    void deleteStockOrder(long stockOrderId) throws SQLException, ClassNotFoundException, InvalidDeleteException;
    int checkOrderNotReceived(long stockOrderId) throws SQLException, ClassNotFoundException;
    int checkOrderExistence(long stockOrderId) throws SQLException, ClassNotFoundException;
    void addStockOrder(StockOrder stockOrder) throws SQLException, ClassNotFoundException;
}
