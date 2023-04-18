package pl.mirekgab.springtransactions.orderitem;

import pl.mirekgab.springtransactions.clientorder.ClientOrder;
import pl.mirekgab.springtransactions.product.Product;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private ClientOrder clientOrder;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private BigInteger net;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigInteger getNet() {
        return net;
    }

    public void setNet(BigInteger net) {
        this.net = net;
    }
}