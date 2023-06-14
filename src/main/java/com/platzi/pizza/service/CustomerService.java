package com.platzi.pizza.service;

import com.platzi.pizza.persintence.entity.CustomerEntity;
import com.platzi.pizza.persintence.entity.PizzaEntity;
import com.platzi.pizza.persintence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity findByPhone(String phone){
        return this.customerRepository.findByPhone(phone);
    }

    /*public List<PizzaEntity> getAvailable(){
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }*/
}
