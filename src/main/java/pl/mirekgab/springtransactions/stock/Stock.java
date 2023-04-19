package pl.mirekgab.springtransactions.stock;

import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.product.Product;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stocks")
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @OneToMany(mappedBy="id")
    private Set<Product> productSet;
}