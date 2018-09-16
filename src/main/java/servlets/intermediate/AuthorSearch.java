package servlets.intermediate;

import com.google.gson.Gson;
import models.Author;
import service.author.AuthorService;
import service.author.AuthorServiceImpl;
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
 * AuthorSearch
 * Created on: May 05, 2018
 * Author: marc
 */
@WebServlet("/Search/Authors")
public class AuthorSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namePrefix = request.getParameter("name");
        if (namePrefix == null) namePrefix = "";
        AuthorService authorService = new AuthorServiceImpl();
        List<Author> authors = authorService.getAuthorsByNamePrefix(namePrefix);
        String json = new Gson().toJson(getReturnModel(authors));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private List<Select2Model> getReturnModel(List<Author> authors) {
        List<Select2Model> list = new LinkedList<Select2Model>();
        for (Author author : authors) {
            Select2Model model = new Select2Model();
            model.id = author.getId();
            model.text = author.getName();
            list.add(model);
        }

        return list;
    }
}
