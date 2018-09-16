package servlets.admin;

import exceptions.DuplicateKeyException;
import models.Author;
import service.author.AuthorService;
import service.author.AuthorServiceImpl;

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
@WebServlet("/admin/authors/add")
public class AddAuthorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add-author.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Author author = new Author();
        author.setName(name);
        AuthorService authorService = new AuthorServiceImpl();
        try {
            long id = authorService.addAuthor(author);
            request.setAttribute("success", "Author added successfully");
        } catch (DuplicateKeyException e) {
            request.setAttribute("error", e.getMessage() + " (" + name +").");
        }

        request.getRequestDispatcher("/admin/add-author.jsp").forward(request, response);
    }
}
