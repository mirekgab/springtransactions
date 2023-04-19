package pl.mirekgab.springtransactions.order;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface OrderToOrderDTOMapper {

    List<OrderDTO> mapEntityListToDTOList(List<Order> ordersList);
    OrderDTO mapEntityToDTO(Order order);
}
