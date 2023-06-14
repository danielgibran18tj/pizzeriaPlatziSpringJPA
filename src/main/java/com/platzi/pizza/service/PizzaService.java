package com.platzi.pizza.service;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import com.platzi.pizza.persintence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persintence.repository.PizzaRepository;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    //private final JdbcTemplate jdbcTemplate;    //permite tener consultas personalizadas
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
        //return this.pizzaRepository.findAll();
    }

    public List<PizzaEntity> getAvailable(){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public List<PizzaEntity> getAvailablePrice(){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        return this.pizzaRepository.findTop3ByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name)     //.orElse(null)
                .orElseThrow(()->new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity> getWith(String ingredient){    //obtener con
        return this.pizzaRepository.searchAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithout(String ingredient){     //obtener sin
        return this.pizzaRepository.searchAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapest(double price){     //encontrar lo mas barato
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    //PAGEABLE
    public Page<PizzaEntity> getAvailablePag(int page, int elements, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
        //return this.pizzaRepository.findAll();
    }

    //USANDO METODOS DIRECTOS DE CRUD REPOSITORY
    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
        //return pizzaRepository.save(pizza);   prueba
    }

    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }

    //actualizando precio de pizza
    @Transactional
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaRepository.updatePrice(dto);
    }


    /*@Autowired
    public PizzaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<PizzaEntity> getAll(){
    return this.jdbcTemplate.query("SELECT * FROM pizza WHERE available = 0", new BeanPropertyRowMapper<>(PizzaEntity.class));}*/
}
