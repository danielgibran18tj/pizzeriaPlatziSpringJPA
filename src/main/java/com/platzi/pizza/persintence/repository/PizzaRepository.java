package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
}
