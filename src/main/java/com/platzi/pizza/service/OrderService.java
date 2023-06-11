package com.platzi.pizza.service;

import com.platzi.pizza.persintence.entity.OrderEntity;
import com.platzi.pizza.persintence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll(){
        List<OrderEntity> orders = this.orderRepository.findAll();
        //comprobar funcionalidad de FetchType.LAZY en OrderEntity
        orders.forEach(o -> System.out.println("nombres: "
                + o.getCustomer().getName()+ " ... celular: "
                + o.getCustomer().getPhoneNumber() ));
        return orders;
    }
}
