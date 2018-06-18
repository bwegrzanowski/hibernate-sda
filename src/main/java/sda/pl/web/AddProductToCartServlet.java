package sda.pl.web;

import sda.pl.domain.Cart;
import sda.pl.domain.Product;
import sda.pl.repository.CartRepository;
import sda.pl.repository.ProductRepository;
import sda.pl.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "AddProductToCartServlet", urlPatterns = "/addProductToCart")
public class AddProductToCartServlet extends HttpServlet {

    //TODO get user id from session or cookie
    public static final long USER_ID = 2L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productAmount = Long.parseLong(request.getParameter("productAmount"));
        Long productId = Long.parseLong(request.getParameter("productId"));

        Cart cart = new Cart();
        Optional<Cart> cartByUserId = CartRepository.findCartByUserId(USER_ID);

        if (cartByUserId.isPresent()) {
            cart = cartByUserId.get();
        } else {
            cart.setUser(UserRepository.findUser(USER_ID).get());
        }

        Optional<Product> product = ProductRepository.findProduct(productId);
        if(product.isPresent()) {
            cart.addProductToCart(product.get(), productAmount);
            CartRepository.saveOrUpdateCart(cart);
        }

        PrintWriter writer = response.getWriter();
        writer.write("productAmount "+ productAmount);
    }
}
