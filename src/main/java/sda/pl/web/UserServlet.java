package sda.pl.web;

import sda.pl.domain.Product;
import sda.pl.domain.User;
import sda.pl.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        List<User> all = UserRepository.;
//        PrintWriter writer = response.getWriter();
//        writer.write("<html><head></head><body><h1>Hello World !!!</h1>");
//
//        writer.write("<table><tr><td>Nazwa</td><td>Id</td></tr>");
//        for (User u : all) {
//            writer.write("<tr><td>" + u.getName() + "</td><td>" + u.getId() + "</td></tr>");
//        }
//
//        writer.write("</table>");
//        writer.write("</body></html>");
    }
}
