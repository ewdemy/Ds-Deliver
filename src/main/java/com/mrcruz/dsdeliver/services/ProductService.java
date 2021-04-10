package com.mrcruz.dsdeliver.services;

import com.mrcruz.dsdeliver.dto.ProductDTO;
import com.mrcruz.dsdeliver.entities.Product;
import com.mrcruz.dsdeliver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        List<Product> list = repository.findAll();

        return list.stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}
