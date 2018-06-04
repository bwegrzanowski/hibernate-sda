package sda.pl.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.w3c.dom.ranges.Range;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int rate;
    String description;
    @ManyToOne
    @JoinColumn
    Product product;
    @ManyToOne
    @JoinColumn
    User user;
    boolean isActive;

}
