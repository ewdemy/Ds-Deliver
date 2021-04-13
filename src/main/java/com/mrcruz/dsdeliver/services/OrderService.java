package com.mrcruz.dsdeliver.services;

import com.mrcruz.dsdeliver.dto.OrderDTO;
import com.mrcruz.dsdeliver.dto.ProductDTO;
import com.mrcruz.dsdeliver.entities.Order;
import com.mrcruz.dsdeliver.entities.OrderStatus;
import com.mrcruz.dsdeliver.entities.Product;
import com.mrcruz.dsdeliver.repositories.OrderRepository;
import com.mrcruz.dsdeliver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<Order> list = repository.findOrderWithProducts();

        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(),
                Instant.now(), OrderStatus.PENDING);

        for(ProductDTO p : dto.getProducts()){
            Product product = productRepository.getOne(p.getId());
            order.getProducts().add(product);
        }

        order = repository.save(order);

        return new OrderDTO(order);

    }

    @Transactional
    public OrderDTO setDelivered(Long id){

        Order order = repository.getOne(id);
        order.setStatus(OrderStatus.DELIVERED);

        order =  repository.save(order);

        return new OrderDTO(order);

    }
}
