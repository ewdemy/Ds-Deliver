package com.mrcruz.dsdeliver.services;

import com.mrcruz.dsdeliver.dto.OrderDTO;
import com.mrcruz.dsdeliver.entities.Order;
import com.mrcruz.dsdeliver.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<Order> list = repository.findOrderWithProducts();

        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }
}
