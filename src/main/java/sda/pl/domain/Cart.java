package sda.pl.domain;

import lombok.*;
import sda.pl.core.ShopException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cartDetailSet")

public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @OneToOne
    @JoinColumn
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    Set<CartDetail> cartDetailSet;

    @Transient
    private boolean valid;

    public void addProductToCart(Product product, Long amount) {
        if (cartDetailSet == null) {
            cartDetailSet = new HashSet<>();
        }
        Optional<CartDetail> first = cartDetailSet.stream().filter(cd -> cd.getProduct().getId().equals(product.getId())).findFirst();
        long sum = product.getStockSumForSale();
        if (!first.isPresent()) {
            if (amount > sum) {
                amount = sum;
            }
            CartDetail newCartDetail = CartDetail.builder()
                    .amount(amount)
                    .price(product.getPrice())
                    .cart(this)
                    .product(product).build();
            cartDetailSet.add(newCartDetail);
        } else {
//            gdy sum = 0 uniemozliwic wstawianie produktu do koszyka
            CartDetail cd = first.get();

            if (cd.getAmount() + amount > sum) {
                amount = sum - cd.getAmount();
            }
            cd.setAmount(cd.getAmount() + amount);
        }
    }



    public void substractProductInCart(Product product) {
        if (cartDetailSet == null) {
            return;
        }

        Optional<CartDetail> productInCart = cartDetailSet.stream().filter(
                cd -> cd.getProduct().getId().equals(product.getId())).findFirst();
        productInCart.ifPresent(cd -> {
            if (cd.getAmount() > 0) {
                cd.setAmount(cd.getAmount() - 1);
            } else {
                cd.setAmount(0L);
            }
        });
    }

    public void changeProductAmount(Product product, Long newAmount) {
        if (cartDetailSet == null) {
            return;
        }
        long sum = product.getStockSumForSale();

        Optional<CartDetail> productInCart = cartDetailSet.stream().filter(
                cd -> cd.getProduct().getId().equals(product.getId())).findFirst();
        productInCart.ifPresent(cd -> {
            if (newAmount > 0) {
                if (newAmount > sum) {
                    cd.setAmount(sum);
                } else {
                    cd.setAmount(newAmount);
                }
                cd.setAmount(newAmount);
            } else {
                cd.setAmount(0L);
            }
        });
    }

    public void checkIsValid() {
        setValid(true);
        getCartDetailSet().forEach(cd-> {
            long sumStockForSale = cd.getProduct().getStockSumForSale();
            if (sumStockForSale < cd.getAmount()) {
                setValid(false);
            }});
    }

    public Order createNewOrder() throws ShopException {
        checkIsValid();
        if (!valid) {
            throw new ShopException("brak czesci produktow na stanie magazynowym");
        }
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setRODO(true);
        order.setUser(this.getUser());
        order.setCityName(this.getUser().getCityName());
        getCartDetailSet().forEach(cd -> order.addOrderDetail(new OrderDetail(cd)));
        order.calculateTotalPrice();
        return order;
    }
}
