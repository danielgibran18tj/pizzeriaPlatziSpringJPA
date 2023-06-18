package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.OrderEntity;
import com.platzi.pizza.persintence.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateBefore(LocalDateTime date);  //antes de la fecha actual
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);  //despues de la fecha actual
    List<OrderEntity> findAllByMethodIn(List<String> methods);        //revisar codigo

    //@Query con SQL nativos
    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOderssss(@Param("id") String idCustomer);

    //QUERY PERSONALIZADO
    @Query(value = "SELECT po.id_order                AS idOrder, " +
            "         cu.name                    AS customerName, " +
            "         po.date                    AS orderDate, " +
            "         po.total                   AS orderTotal, " +
            "         STRING_AGG (pi.name, ', ')     AS pizzasNames " +
            "    FROM pizza_order po " +
            "         INNER JOIN customer cu ON po.id_customer = cu.id_customer " +
            "         INNER JOIN order_item oi ON po.id_order = oi.id_order " +
            "         INNER JOIN pizza pi ON oi.id_pizza = pi.id_pizza " +
            "   WHERE po.id_order = :orderId " +
            "GROUP BY po.id_order, " +
            "         cu.name, " +
            "         po.date, " +
            "         po.total", nativeQuery = true)
    OrderSummary findSumaryy(@Param("orderId") int orderId);

    //lo sgte es un ejemplo del QUERY PERSONALIZADO con un filtro mas (oi.price)
    @Query(value = "SELECT po.id_order                AS idOrder,\n" +
            "         cus.name                    AS customerName,\n" +
            "         po.date                    AS orderDate,\n" +
            "         po.total                   AS orderTotal,\n" +
            "         STRING_AGG (pi.name, ', ')     AS pizzaNames,\n" +
            "         oi.price\n" +
            "    FROM pizza_order po\n" +
            "         INNER JOIN customer cus ON po.id_customer = cus.id_customer\n" +
            "         INNER JOIN order_item oi ON po.id_order = oi.id_order\n" +
            "         INNER JOIN pizza pi ON oi.id_pizza = pi.id_pizza\n" +
            "   WHERE po.id_order = :orderId and oi.price <= 20.00\n" + //cuando el precio es menor y igual que 20
            "   GROUP BY po.id_order,\n" +
            "         cus.name,\n" +
            "         po.date,\n" +
            "         po.total,\n" +
            "         oi.price", nativeQuery = true)
    OrderSummary findSumaryyexample(@Param("orderId") int orderId);


    /*@Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("id_customer") String idCustomer, @Param("method") String method);*/
    @Procedure(procedureName = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(
            @Param("id_customer") String idCustomer,
            @Param("method") String method);
}