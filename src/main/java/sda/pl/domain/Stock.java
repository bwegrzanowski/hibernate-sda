package sda.pl.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated
    WarehouseName warehouseName;
    BigDecimal amount;

    @JoinColumn
    @ManyToOne
    Product product;

}
