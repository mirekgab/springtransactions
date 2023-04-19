package pl.mirekgab.springtransactions.order;

import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.orderitem.OrderItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderItem> orderItemSet;

    private BigDecimal net;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}