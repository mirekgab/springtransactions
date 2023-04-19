package pl.mirekgab.springtransactions.invoice;


import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="client_order_id", referencedColumnName = "id")
    private Order order;

}