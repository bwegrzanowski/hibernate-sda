package sda.pl.web;

import sda.pl.core.ShopException;
import sda.pl.domain.Cart;
import sda.pl.domain.Order;
import sda.pl.repository.CartRepository;
import sda.pl.repository.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CreateOrderServlet", urlPatterns = "/createOrder")
public class CreateOrderServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long USER_ID = 2L;
        Optional<Cart> cartByUserId = CartRepository.findCartByUserId(USER_ID);
        if (cartByUserId.isPresent()) {
            Order newOrder = null;
            try {
                newOrder = cartByUserId.get().createNewOrder();
            } catch (ShopException e) {
                e.printStackTrace();
            }
            OrderRepository.saveOrder(newOrder);
            CartRepository.deleteCart(cartByUserId.get());
            response.getWriter().write("Zlozono zamowienie");

        }
    }
}
