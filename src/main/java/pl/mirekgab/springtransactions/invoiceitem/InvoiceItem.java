package pl.mirekgab.springtransactions.invoiceitem;

import lombok.Getter;
import lombok.Setter;
import pl.mirekgab.springtransactions.invoice.Invoice;
import pl.mirekgab.springtransactions.product.Product;
import pl.mirekgab.springtransactions.stock.Stock;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;
    @ManyToOne
    private Stock stock;

    private Integer quantity;
    private BigInteger net;

}