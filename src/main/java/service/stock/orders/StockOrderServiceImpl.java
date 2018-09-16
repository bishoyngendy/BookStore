package service.stock.orders;

import dao.stock.order.StockOrderDao;
import dao.stock.order.StockOrderDaoImpl;
import exceptions.EmptyResultSetException;
import exceptions.InvalidDeleteException;
import exceptions.InvalidUpdateException;
import models.StockOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockOrderServiceImpl implements StockOrderService {

    private StockOrderDao stockOrderDao;

    public StockOrderServiceImpl() {
        this.stockOrderDao = new StockOrderDaoImpl();
    }

    public List<StockOrder> getStockOrders(int start, int length, boolean received) {
        try {
            return this.stockOrderDao.getStockOrders(start, length, received);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void receiveStockOrder(long stockOrderId) throws InvalidUpdateException {
        try {
            this.stockOrderDao.receiveStockOrder(stockOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteStockOrder(long stockOrderId) throws InvalidDeleteException {
        try {
            this.stockOrderDao.deleteStockOrder(stockOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkOrderNotReceived(long stockOrderId) throws EmptyResultSetException {
        int count = 0;
        try {
            count = this.stockOrderDao.checkOrderNotReceived(stockOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (count == 0) {
            throw new EmptyResultSetException("Invalid Stock Order Id");
        }
    }

    public void checkOrderExistence(long stockOrderId) throws EmptyResultSetException {
        int count = 0;
        try {
            count = this.stockOrderDao.checkOrderExistence(stockOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (count == 0) {
            throw new EmptyResultSetException("Invalid Stock Order Id");
        }
    }

    public void addStockOrder(StockOrder stockOrder) {
        try {
            this.stockOrderDao.addStockOrder(stockOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long getStockOrdersTotalCount(boolean received) {
        try {
            return this.stockOrderDao.getStockOrdersTotalCount(received);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<StockOrder> getStockOrders(int start, int length, boolean received, String search) {
        try {
            return this.stockOrderDao.getStockOrders(start, length, received, search);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public long getStockOrdersTotalCount(boolean received, String search) {
        try {
            return this.stockOrderDao.getStockOrdersTotalCount(received, search);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
