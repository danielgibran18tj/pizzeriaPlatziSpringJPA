package com.platzi.pizza.web.controller;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired  //no es necesario agg pero tampoco esta de mas
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getAvailable(){    //traer pizzas disponibles ordenada por precio
        return ResponseEntity.ok(this.pizzaService.getAvailable());
    }

    @GetMapping("/availableprice")
    public ResponseEntity<List<PizzaEntity>> getAvailablePrice(){    //traer 3 pizzas disponibles mas baratas
        return ResponseEntity.ok(this.pizzaService.getAvailablePrice());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> get(@PathVariable int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @GetMapping("/with/{ingredient}")    //obtener con
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    @GetMapping("/without/{ingredient}")    //obtener sin
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }

    @GetMapping("/cheapest/{price}")    //encontrar lo mas barato
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable double price){
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    //PAGEABLE
    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "8") int elements){
        return ResponseEntity.ok(this.pizzaService.getAll(page,elements));
    }

    @GetMapping("/availablePage")
    public ResponseEntity<Page<PizzaEntity>> getAvailablePage(@RequestParam(defaultValue = "0") int page
                                                            ,@RequestParam(defaultValue = "8") int elements
                                                            ,@RequestParam(defaultValue = "price") String sortBy
                                                            ,@RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(this.pizzaService.getAvailablePag(page, elements, sortBy, sortDirection));
    }

    //USANDO METODOS DIRECTOS DE CRUD REPOSITORY
    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
        if(pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())){
            //return ResponseEntity.ok(this.pizzaService.save(pizza));
            return new ResponseEntity<>(pizzaService.save(pizza), HttpStatus.CREATED);    //prueba
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
        if(pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable int idPizza){
        if (this.pizzaService.exists(idPizza)){
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    //actualizando precio de pizza
    @PutMapping("/price")
    public ResponseEntity<PizzaEntity> updatePrice(@RequestBody UpdatePizzaPriceDto dto) {
        if( this.pizzaService.exists(dto.getPizzaId())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

}
