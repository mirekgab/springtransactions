package pl.mirekgab.springtransactions.orderitem;

import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.order.Order;
import pl.mirekgab.springtransactions.product.Product;
import pl.mirekgab.springtransactions.stock.Stock;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
    @ManyToOne
    private Stock stock;

    private Integer quantity;
    private BigInteger net;

}