package pl.mirekgab.springtransactions.invoice;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @JoinColumn(name="client_order_id", referencedColumnName = "id")
    private Order order;

}