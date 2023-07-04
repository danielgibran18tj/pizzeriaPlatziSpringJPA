package com.platzi.pizza.persintence.projection;

import java.time.LocalDate;

public interface OrderSummary {     //QUERY PERSONALIZADO
    //estos nos ayudan consultar a la base de datos info especifica que queremos, que la base haga el proceso
    //requeriremos datos de distintas tablas
    //no es necesario escribir los nombres de los metodos perfectamente
    Integer getIdOrder();
    String getCustomerName();
    LocalDate getOrderDate();
    Double getOrderTotal();    //valor total de la orden
    String getPizzaNames();    //nombres de las pizzas



}
