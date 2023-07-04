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

    @GetMapping("/available")       //mostrar pizzas disponibles (admin - customer)
    @CrossOrigin(origins = "http://localhost:4200")//senialando que tiene q permitir las peticiones que lleguen desde este metodo
    public ResponseEntity<List<PizzaEntity>> getAvailable(){    //traer pizzas disponibles ordenada por precio
        return ResponseEntity.ok(this.pizzaService.getAvailable());
    }

    @GetMapping("/availableprice")      //traer 3 pizzas disponibles mas baratas   (admin - customer)
    public ResponseEntity<List<PizzaEntity>> getAvailablePrice(){
        return ResponseEntity.ok(this.pizzaService.getAvailablePrice());
    }

    @GetMapping("/name/{name}")     //mostrar pizza disponible por nombre (ADMIN - CUSTOMER)
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/{ingredient}")    //obtener con X ingrediente (ADMIN - CUSTOMER)
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    @GetMapping("/without/{ingredient}")    //obtener sin X ingrediente (ADMIN - CUSTOMER)
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }

    @GetMapping("/cheapest/{price}")    // pizzas menores a X precio (admin - customer)
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable double price){
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    //PAGEABLE
    @GetMapping         //MOSTRAR TODAS LAS PIZZAS PAGEABLE
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "8") int elements){
        return ResponseEntity.ok(this.pizzaService.getAll(page,elements));
    }

    @GetMapping("/availablePage")       //pageable de 8, precio asc ADMIN - CUSTOMER
    public ResponseEntity<Page<PizzaEntity>> getAvailablePage(@RequestParam(defaultValue = "0") int page
                                                            ,@RequestParam(defaultValue = "8") int elements
                                                            ,@RequestParam(defaultValue = "price") String sortBy
                                                            ,@RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(this.pizzaService.getAvailablePag(page, elements, sortBy, sortDirection));
    }

    //USANDO METODOS DIRECTOS DE CRUD REPOSITORY
    @GetMapping("/{idPizza}")       //pizza segun idPizza (ADMIN - CUSTOMER)
    public ResponseEntity<PizzaEntity> get(@PathVariable int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @PostMapping    //AGREGAR NUEVA PIZZA (ADMIN)
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
        if(pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
            //return new ResponseEntity<>(pizzaService.save(pizza), HttpStatus.CREATED);    //prueba
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    @PutMapping     //ACTUALIZAR PIZZA (ADMIN)
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
        if(pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    @DeleteMapping("/{idPizza}")        //Eliminar pizza (ADMIN)
    public ResponseEntity<Void> delete(@PathVariable int idPizza){
        if (this.pizzaService.exists(idPizza)){
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

    //actualizando precio de pizza
    @PutMapping("/price")       //actualiza precio de X pizza (ADMIN) - con error controlado
    public ResponseEntity<PizzaEntity> updatePrice(@RequestBody UpdatePizzaPriceDto dto) {
        if( this.pizzaService.exists(dto.getPizzaId())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();        }
        return ResponseEntity.badRequest().build(); //no se procesa la peticion
    }

}
