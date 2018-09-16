package servlets.admin;

import exceptions.DuplicateKeyException;
import models.Author;
import models.Publisher;
import service.author.AuthorService;
import service.author.AuthorServiceImpl;
import service.publisher.PublisherService;
import service.publisher.PublisherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AddAuthorServlet
 * Created on: May 05, 2018
 * Author: marc
 */
@WebServlet("/admin/publishers/add")
public class AddPublisherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add-publisher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Publisher publisher = new Publisher();
        publisher.setName(name);
        publisher.setAddress(address);
        publisher.setPhone(getPhone(phone));

        PublisherService publisherService = new PublisherServiceImpl();
        try {
            long id = publisherService.addPublisher(publisher);
            request.setAttribute("success", "Publisher added successfully");
        } catch (DuplicateKeyException e) {
            request.setAttribute("error", e.getMessage() + " (" + name +").");
        }

        request.getRequestDispatcher("/admin/add-publisher.jsp").forward(request, response);
    }

    private String getPhone(String phone) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(phone.substring(1, 4))
                .append(phone.substring(6, 10))
                .append(phone.substring(11));

        return stringBuilder.toString();
    }
}
