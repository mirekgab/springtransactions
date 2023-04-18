package pl.mirekgab.springtransactions.stockquantity;

import pl.mirekgab.springtransactions.product.Product;
import pl.mirekgab.springtransactions.stock.Stock;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "stock_quantity")
public class StockQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Stock stock;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private BigInteger quantityValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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

    public BigInteger getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(BigInteger quantityValue) {
        this.quantityValue = quantityValue;
    }
}