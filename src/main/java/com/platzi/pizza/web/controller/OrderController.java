package com.platzi.pizza.web.controller;

import com.platzi.pizza.persintence.entity.OrderEntity;
import com.platzi.pizza.persintence.projection.OrderSummary;
import com.platzi.pizza.service.OrderService;
import com.platzi.pizza.service.dto.RandonOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping     //Mostrar las ordenes (ADMIN)
    public ResponseEntity<List<OrderEntity>> getAll(){
        return ResponseEntity.ok(this.orderService.getAll());
    }

    @GetMapping("/today")   //mostrar ordenes con fecha actual (admin)
    public ResponseEntity<List<OrderEntity>> getTodayOrders(){
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }

    @GetMapping("/outside") //solo metodos de pago D o C (ADMIN)
    public ResponseEntity<List<OrderEntity>> getOutsideOrders(){        //revisar codigo
        return ResponseEntity.ok(this.orderService.getOutsideOrders());
    }

    @GetMapping("/customer/{id}")   //mostrar orden de X cliente
    public ResponseEntity<List<OrderEntity>> getOutsideOrders(@PathVariable String id){        //revisar codigo
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }

    //QUERY PERSONALZADO
    @GetMapping("/summary/{id}")    //mostrar diferentes datos referentes de X order (admin)
    public ResponseEntity<OrderSummary> getSummary(@PathVariable int id){        //revisar codigo
        return ResponseEntity.ok(this.orderService.getSummary(id));
    }

    //stored procedure - procedimiento almacenado
    @PostMapping("/random")   //PROMO EN PIZZA AL AZAR (ADMIN - CUSTOMER)
    public ResponseEntity<Boolean> randomOrder(@RequestBody RandonOrderDto dto){
        return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
    }
}
