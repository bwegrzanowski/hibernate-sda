package sda.pl;

import sda.pl.domain.*;
import sda.pl.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class App {

    public static void main(String[] args) {

        Product maslo = Product.builder()
                .name("Maslo")
                .color(Color.RED)
                .price(Price.builder()
                        .priceGross(new BigDecimal("6.50"))
                        .priceNet(new BigDecimal("4.50"))
                        .priceSymbol("PLN").build()).build();
        ProductRepository.saveProduct(maslo);

        Optional<Product> product = ProductRepository.findProduct(2L);
        product.ifPresent(p -> System.out.println(p.getName()));

        ProductRepository.findAll().forEach(p -> System.out.println(p.getId() + " " + p.getName()));

        ProductRepository.findAllWithPriceNetLessThan(new BigDecimal("5"))
                .forEach(p -> System.out.println("cena mniejsza niz 5 " + p.getName()));


        Long sum = ProductRepository.countAll();
        System.out.println("suma: " + sum);
        Optional<Product> product1 = ProductRepository.findProduct(9L);
        if (product1.isPresent()) {
            Product product10 = product1.get();
            product10.setName("Kefir");
            product10.setPrice(Price.builder()
                    .priceNet(new BigDecimal("5"))
                    .priceGross(new BigDecimal("5.5"))
                    .priceSymbol("PLN").build());
            ProductRepository.saveOrUpdate(product10);


//            Order kowalskiOrder = Order.builder()
//                    .date(LocalDateTime.now())
////                    .email("kowalski@gmail.com")
//                    .RODO(true)
//                    .cityName("Poznan")
//                    .totalPrice(new Price())
//                    .build();
//
//            OrderDetail detail1 = OrderDetail.builder()
//                    .amount(5L)
//                    .product(product10)
//                    .price(product10.getPrice()).build();
//
//            kowalskiOrder.addOrderDetail(detail1);
//            kowalskiOrder.calculateTotalPrice();
//
//
//            OrderRepository.saveOrder(kowalskiOrder);

            OrderRepository.findAll();
            OrderRepository.findAll().forEach(o -> o.getOrderDetailSet()
                    .forEach(od -> System.out.println(od.getProduct().getName())));

            OrderRepository.findAllWithProductName("Kefir").forEach(o -> o.getOrderDetailSet()
                    .forEach(od -> System.out.println("zamowienia z kefirem " + od.getProduct().getName())));
        }

        UserRepository.findAllWithTotalOrderPrice()
                .forEach(u -> System.out.println(u.getEmail() + " " + u.getTotalOrderPrice()));

        ProductRepository.findByNameCriteriaQuery("KEFI").forEach(p -> System.out.println("criteria: " + p.getName()));

        List<AdvertisingBanner> all = AdvertisingBannerRepository.findAll();
        all.forEach(a -> System.out.println(a.getId()));
    }
}