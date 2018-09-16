package service.stock.orders;

import exceptions.EmptyResultSetException;
import exceptions.InvalidDeleteException;
import exceptions.InvalidUpdateException;
import models.StockOrder;

import java.sql.SQLException;
import java.util.List;

public interface StockOrderService {
    List<StockOrder> getStockOrders(int start, int length, boolean received);
    long getStockOrdersTotalCount(boolean received);

    List<StockOrder> getStockOrders(int start, int length, boolean received, String search);
    long getStockOrdersTotalCount(boolean received, String search);

    void receiveStockOrder(long stockOrderId) throws InvalidUpdateException;
    void deleteStockOrder(long stockOrderId) throws InvalidDeleteException;
    void checkOrderNotReceived(long stockOrderId) throws EmptyResultSetException;
    void checkOrderExistence(long stockOrderId) throws EmptyResultSetException;
    void addStockOrder(StockOrder stockOrder);


}
