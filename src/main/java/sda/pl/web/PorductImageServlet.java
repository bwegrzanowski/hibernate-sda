package sda.pl.web;

import sda.pl.domain.Product;
import sda.pl.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PorductImageServlet", urlPatterns = "/productImage")
public class PorductImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getParameter("productId"));
        Optional<Product> product = ProductRepository.findProduct(productId);

        if (product.isPresent()) {
            try {
                product.get().getProductImage().getImage();
            } catch (Exception e) {
                response.getWriter().write("nie znaleziono obrazka");
            } finally {
                response.setHeader("Content-Length", String.valueOf(product.get().getProductImage().getImage().length));
                response.setHeader("Content-Disposition", "inline; filename=\"" + product.get().getName() + "\"");

                // Write image data to Response.
                response.getOutputStream().write(product.get().getProductImage().getImage());
            }
        }

    }
}
