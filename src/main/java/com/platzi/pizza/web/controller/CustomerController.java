package com.platzi.pizza.web.controller;

import com.platzi.pizza.persintence.entity.CustomerEntity;
import com.platzi.pizza.persintence.entity.OrderEntity;
import com.platzi.pizza.service.CustomerService;
import com.platzi.pizza.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerEntity> getByPhone(@PathVariable String phone){    //traer pizzas disponibles ordenada por precio
        return ResponseEntity.ok(this.customerService.findByPhone(phone));
    }

    @GetMapping("/customer/{id}")   //ACCESO SOLO PARA ADMIN, REALIZADO EN SERVICE
    public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable String id){        //revisar codigo
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }
}
