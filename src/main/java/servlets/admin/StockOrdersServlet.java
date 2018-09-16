package servlets.admin;

import com.google.gson.Gson;
import exceptions.EmptyResultSetException;
import exceptions.InvalidDeleteException;
import exceptions.InvalidUpdateException;
import models.StockOrder;
import service.stock.orders.StockOrderService;
import service.stock.orders.StockOrderServiceImpl;
import servlets.models.DatatableModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/orders/stocks")
public class StockOrdersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stockOrderId = request.getParameter("stockOrderId");
        String isbn = request.getParameter("isbn");
        String quantity = request.getParameter("quantity");
        StockOrderService stockOrderService = new StockOrderServiceImpl();
        if (isbn != null && quantity != null) {
            StockOrder stockOrder = new StockOrder();
            stockOrder.setISBN(isbn);
            stockOrder.setQuantity(Integer.valueOf(quantity));
            stockOrderService.addStockOrder(stockOrder);
            sendSuccessResponse(response);
        } else if (request.getParameter("receive") != null) {
            try {
                stockOrderService.checkOrderNotReceived(Long.valueOf(stockOrderId));
                stockOrderService.receiveStockOrder(Long.valueOf(stockOrderId));
                sendSuccessResponse(response);
            } catch (EmptyResultSetException e) {
                request.setAttribute("value", stockOrderId);
                request.setAttribute("error", e.getMessage());
            } catch (InvalidUpdateException e) {
                request.setAttribute("value", stockOrderId);
                request.setAttribute("error", e.getMessage());
            }
        } else if (request.getParameter("delete") != null) {
            try {
                stockOrderService.checkOrderExistence(Long.valueOf(stockOrderId));
                stockOrderService.deleteStockOrder(Long.valueOf(stockOrderId));
                sendSuccessResponse(response);
            } catch (EmptyResultSetException e) {
                request.setAttribute("value", stockOrderId);
                request.setAttribute("error", e.getMessage());
            } catch (InvalidDeleteException e) {
                request.setAttribute("value", stockOrderId);
                request.setAttribute("error", e.getMessage());
            }
        }
        request.getRequestDispatcher("/admin/stock-orders.jsp").forward(request, response);
    }

    private void sendSuccessResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("test/html");
        response.getWriter().write("success");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String received = request.getParameter("received");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String search = request.getParameter("search[value]");
        StockOrderService stockOrderService = new StockOrderServiceImpl();
        List<StockOrder> stockOrders;
        long totalCount;
        if (search == null || search.equals("")) {
            if (received == null) {
                request.getRequestDispatcher("/admin/stock-orders.jsp").forward(request, response);
                return;
            } else if (received.equals("true")) {
                stockOrders = stockOrderService.getStockOrders(Integer.valueOf(start), Integer.valueOf(length), true);
                totalCount = stockOrderService.getStockOrdersTotalCount(true);
            } else {
                stockOrders = stockOrderService.getStockOrders(Integer.valueOf(start), Integer.valueOf(length), false);
                totalCount = stockOrderService.getStockOrdersTotalCount(false);
            }
        } else {
            if (received == null) {
                request.getRequestDispatcher("/admin/stock-orders.jsp").forward(request, response);
                return;
            } else if (received.equals("true")) {
                stockOrders = stockOrderService.getStockOrders(Integer.valueOf(start), Integer.valueOf(length), true, search);
                totalCount = stockOrderService.getStockOrdersTotalCount(true, search);
            } else {
                stockOrders = stockOrderService.getStockOrders(Integer.valueOf(start), Integer.valueOf(length), false, search);
                totalCount = stockOrderService.getStockOrdersTotalCount(false, search);
            }
        }
        DatatableModel datatableModel = buildDataTableModel(stockOrders, totalCount);
        String json = new Gson().toJson(datatableModel);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private DatatableModel buildDataTableModel(List<StockOrder> stockOrders, long totalCount) {
        DatatableModel datatableModel = new DatatableModel();
        datatableModel.setRecordsTotal(totalCount);
        datatableModel.setRecordsFiltered(totalCount);
        for (StockOrder stockOrder : stockOrders) {
            List<String> strings = new ArrayList<String>();
            strings.add(stockOrder.getId() + "");
            strings.add(stockOrder.getISBN());
            strings.add(stockOrder.getBookTitle());
            strings.add(stockOrder.getPublisherName());
            strings.add(stockOrder.getQuantity() + "");
            datatableModel.getData().add(strings);
        }
        return datatableModel;
    }
}
