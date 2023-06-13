package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();     //traer pizzas disponibles ordenada por precio
    List<PizzaEntity> findTop3ByAvailableTrueOrderByPrice();   //encontrar las 3 pizza disponible mas barata
    PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);  //IgnoreCase seniala que ignore si es mayuscula o minuscula
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);  //IgnoreCase seniala que ignore si es mayuscula o minuscula
    //encontrar la primera pizza disponible con X nombre
    List<PizzaEntity> searchAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);   //traer lista de pizza disponibles con X palabra en la descripcion
    List<PizzaEntity> searchAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);    //Traer lista de pizza disponibles sin X palabra en la descripcion
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);
    int countByVeganTrue(); //declarado en getAvailable
}
