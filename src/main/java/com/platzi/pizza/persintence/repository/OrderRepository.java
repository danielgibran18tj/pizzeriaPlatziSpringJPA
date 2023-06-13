package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateBefore(LocalDateTime date);  //antes de la fecha actual
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);  //despues de la fecha actual
    List<OrderEntity> findAllByMethodIn(List<String> methods);        //revisar codigo
}
