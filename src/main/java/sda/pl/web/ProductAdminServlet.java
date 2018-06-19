package sda.pl.web;

import sda.pl.domain.Color;
import sda.pl.domain.Price;
import sda.pl.domain.Product;
import sda.pl.domain.ProductType;
import sda.pl.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "ProductAdminServlet", urlPatterns = "/productAdmin")
public class ProductAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        BigDecimal netPrice = new BigDecimal(request.getParameter("netPrice"));
        BigDecimal grossPrice = new BigDecimal(request.getParameter("grossPrice"));
        Color color = Color.valueOf(request.getParameter("color"));
        ProductType productType = ProductType.valueOf(request.getParameter("productType"));


        Product product = Product.builder()
                .name(name)
                .color(color)
                .price(Price.builder()
                        .priceGross(grossPrice)
                        .priceNet(netPrice).priceSymbol("PLN").build())
                .productType(productType)
                .build();

        ProductRepository.saveOrUpdateProduct(product);


    }


}