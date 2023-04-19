package pl.mirekgab.springtransactions.stockquantity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.product.Product;
import pl.mirekgab.springtransactions.stock.Stock;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_quantity")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StockQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private BigDecimal quantityValue;

}