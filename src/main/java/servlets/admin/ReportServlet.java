package servlets.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import utils.ConnectionProvider;

@WebServlet("/admin/report")
public class ReportServlet extends HttpServlet {
    private static final String[] reports = {
            "total_sales", "total_sales_each_book", "top_5_customers_books_count", "top_5_customers_books_profit", "top_10_books_profit", "top_10_books_amount"
    };

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportIndexString = request.getParameter("reportIndex");
        Integer reportIndex = Integer.parseInt(reportIndexString);
        try {
            reportIndex = Integer.parseInt(reportIndexString);
            if (reportIndex < 0 || reportIndex > 5) {
                response.setStatus(400);
                response.getWriter().write("INVALID REQUEST");
                response.getWriter().close();
                return;
            }
        } catch(Exception e) {
            response.setStatus(400);
            response.getWriter().write("INVALID REQUEST");
            response.getWriter().close();
            return;
        }

        try {
            String path = getServletContext().getRealPath("/");
            path = path +"jrxml/";
            JasperDesign jasperDesign = JRXmlLoader.load(path + reports[reportIndex] +".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parameters = new HashMap<String,Object>();
            parameters.put("basePath", path);
            /** PDF **/

            byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters,
                    ConnectionProvider.getConnection());
            response.setContentType("application/pdf");
            OutputStream out = response.getOutputStream();
            out.write(byteStream, 0, byteStream.length);
            out.flush();
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}