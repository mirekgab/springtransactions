package pl.mirekgab.springtransactions.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private Long id;
    private BigDecimal net;
}
