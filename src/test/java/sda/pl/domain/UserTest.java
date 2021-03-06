package sda.pl.domain;

import org.junit.Assert;
import org.junit.Test;
import sda.pl.repository.ProductRatingRepository;
import sda.pl.repository.ProductRepository;
import sda.pl.repository.UserRepository;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void rateProduct() {
        Optional<User> user = UserRepository.findUser(1L);

        user.ifPresent(u -> {
            ProductRating productRating =
                    u.rateProduct(1, "kiepskie", ProductRepository.findProduct(2L).get());
            ProductRatingRepository.saveOrUpdate(productRating);
        });
    }

    @Test
    public void testFindAll() {
        Assert.assertTrue(ProductRatingRepository.findAllActiveByProduct(2L).size() == 1);
    }
}