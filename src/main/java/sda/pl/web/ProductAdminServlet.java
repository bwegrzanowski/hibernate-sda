package sda.pl.web;

import sda.pl.ProjectHelper;
import sda.pl.domain.Color;
import sda.pl.domain.Price;
import sda.pl.domain.Product;
import sda.pl.domain.ProductType;
import sda.pl.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet(name = "ProductAdminServlet", urlPatterns = "/productAdmin")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// max czesc pliku 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
public class ProductAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = ProjectHelper.convertStringToLong(request.getParameter("productId"));
        String name = request.getParameter("name");
        BigDecimal netPrice = ProjectHelper.convertStringToBigDecimal(request.getParameter("netPrice"));
        BigDecimal grossPrice = ProjectHelper.convertStringToBigDecimal(request.getParameter("grossPrice"));
        Color color = Color.valueOf(request.getParameter("color"));
        ProductType productType = ProductType.valueOf(request.getParameter("productType"));

        Product productFromDB = ProductRepository.findProduct(id).orElse(new Product());

        Product product = Product.builder()
                .name(name)
                .color(color)
                .price(Price.builder()
                        .priceGross(grossPrice)
                        .priceNet(netPrice).priceSymbol("PLN").build())
                .productType(productType)
                .id(productFromDB != null ? productFromDB.getId() : null)
                .build();

        Part photo = request.getPart("photo");
        if(photo.getSize()>0 && photo!= null) {
            product.addProductImage(photo);
        }else{
            product.setProductImage(productFromDB.getProductImage());
        }

        ProductRepository.saveOrUpdateProduct(product);


    }


}