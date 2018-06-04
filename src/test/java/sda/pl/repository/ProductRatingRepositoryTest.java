package sda.pl.repository;

import org.junit.Test;

public class ProductRatingRepositoryTest {

    @Test
    public void findAllActiveByProduct() {
        ProductRatingRepository.findAllActiveByProduct(4L) ;
    }
}