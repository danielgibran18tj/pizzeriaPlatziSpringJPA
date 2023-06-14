package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

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

    //actualizando precio de pizza
    @Query(value =
            "UPDATE pizza "+
            "SET price = :#{#newPizzaPrice.newPrice} "+
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId} ", nativeQuery = true)
    @Modifying //indispensable para funciones de create, update and delete
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);

    //otra forma de declarar nuestras variables en el QUERY NATIVO
     /*@Query(value =
            "UPDATE pizza "+
            "SET price = :newPrice "+
            "WHERE id_pizza = :idPizza", nativeQuery = true)
    @Modifying //indispensable para funciones de create, update and delete
    void updatePrice(@Param("idPizza") int idPizza, @Param("newPrice") double newPrice);
    */
}
