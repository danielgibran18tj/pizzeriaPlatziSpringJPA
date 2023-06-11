package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {

}
