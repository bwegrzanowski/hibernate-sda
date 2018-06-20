package sda.pl.domain;

import lombok.*;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDetailSet", "cartDetailSet", "productImage",
        "productRatingSet", "stockSet"})

public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;

    @Enumerated
    ProductType productType;

    @Embedded
    Price price;

    @Enumerated(EnumType.STRING)
    Color color;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    ProductImage productImage;

    @OneToMany(mappedBy = "product")
    Set<OrderDetail> orderDetailSet;

    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetailSet;

    @OneToMany(mappedBy = "product")
    Set<ProductRating> productRatingSet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Stock> stockSet;

    public void addStock(WarehouseName name, BigDecimal amount) {
        if (stockSet == null) {
            stockSet = new HashSet<>();
        }
        Optional<Stock> stockExist = stockSet.stream().filter(s -> s.getWarehouseName()
                .equals(name)).findFirst();
        if (!stockExist.isPresent()) {
            Stock stock = new Stock();
            stock.setProduct(this);
            stock.setWarehouseName(name);
            stock.setAmount(amount);
            stockSet.add(stock);

        } else {
            stockExist.ifPresent(s -> s.setAmount(s.getAmount().add(amount)));
        }
    }

    public long getStockSumForSale() {
        return getStockSet().stream().filter(stock -> !stock.getWarehouseName()
                .equals(WarehouseName.COMPLAINT)).mapToLong(s -> s.getAmount().longValue()).sum();
    }

    public void addProductRating(ProductRating productRating) {
        if (productRatingSet == null) {
            productRatingSet = new HashSet<>();
        }
        productRating.setProduct(this);
        productRatingSet.add(productRating);
    }

    public void addProductImage(Part photo) throws IOException {
        InputStream input = photo.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        for (int length = 0; (length = input.read(buffer)) > 0; ) output.write(buffer, 0, length);

        ProductImage productImage = new ProductImage();
        productImage.setImage(output.toByteArray());
        productImage.setProduct(this);

        this.setProductImage(productImage);
    }
}

