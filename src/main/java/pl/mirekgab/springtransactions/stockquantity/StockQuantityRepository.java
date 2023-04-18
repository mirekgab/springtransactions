package pl.mirekgab.springtransactions.stockquantity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockQuantityRepository extends JpaRepository<StockQuantity, Long> {
}