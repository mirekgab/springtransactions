package pl.mirekgab.springtransactions.stockquantity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mirekgab.springtransactions.product.Product;
import pl.mirekgab.springtransactions.product.ProductRepository;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class StockQuantityService {
    private final StockQuantityRepository stockQuantityRepository;
    private final ProductRepository productRepository;

    public void decreaseQuanity(Long productId, Long stockId, Integer quantity) {
        StockQuantity stockQuantity = stockQuantityRepository.findByStockIdAndProductId(stockId, productId)
                .orElseThrow(()->new RuntimeException(String.format("productId=%d not exists in stockId=%d ", productId, stockId)));
        stockQuantity.setQuantity(stockQuantity.getQuantity()-quantity);
        Product product = productRepository.findById(stockQuantity.getProduct().getId()).orElseThrow(
                () -> new RuntimeException(String.format("product with id=%d not exists", stockQuantity.getProduct().getId()))
        );
        stockQuantity.setQuantityValue(
                stockQuantity.getQuantityValue().subtract(
                        product.getPrice()
                                .multiply(BigDecimal.valueOf(quantity))
                )
        );
        stockQuantityRepository.save(stockQuantity);
    }

    public int availableQuantity(Long stockId, Long productId) {
        StockQuantity quantityInStock = stockQuantityRepository.findByStockIdAndProductId(stockId, productId)
                .orElseThrow(()->new RuntimeException(String.format("productId=%d not exists in stockId=%d ", productId, stockId)));
        return quantityInStock.getQuantity();
    }
}
