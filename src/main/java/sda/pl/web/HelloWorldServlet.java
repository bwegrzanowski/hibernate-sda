package sda.pl.web;

import sda.pl.domain.Product;
import sda.pl.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HelloWorldServlet", urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String firstName = request.getParameter("firstName");

        List<Product> all = new ArrayList<>();
        Product product = new Product();
        Product product2 = new Product();
        product.setName("maslo");
        product2.setName("kefir");
        all.add(product);

        PrintWriter writer = response.getWriter();
        writer.write("<html><head></head><body><h1>Hello World !!!</h1>");

        writer.write("<table><tr><td>Nazwa</td><td>Id</td></tr>");
        for (Product p : all) {
            writer.write("<tr><td>" + p.getName() + "</td><td>" + p.getId() + "</td></tr>");
        }

        writer.write("</table>");
        writer.write("</body></html>");
    }
}
