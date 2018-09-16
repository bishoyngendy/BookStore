package servlets.intermediate;

import com.google.gson.Gson;
import models.Publisher;
import service.publisher.PublisherService;
import service.publisher.PublisherServiceImpl;
import servlets.models.Select2Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * PublisherSearch
 * Created on: May 05, 2018
 * Author: marc
 */
@WebServlet("/Search/Publishers")
public class PublisherSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namePrefix = request.getParameter("name");
        if (namePrefix == null) namePrefix = "";
        PublisherService publisherService = new PublisherServiceImpl();
        List<Publisher> publishers = publisherService.getPublishersByNamePrefix(namePrefix);
        String json = new Gson().toJson(getReturnModel(publishers));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private List<Select2Model> getReturnModel(List<Publisher> publishers) {
        List<Select2Model> list = new LinkedList<Select2Model>();
        for (Publisher publisher : publishers) {
            Select2Model model = new Select2Model();
            model.id = publisher.getId();
            model.text = publisher.getName();
            list.add(model);
        }

        return list;
    }
}