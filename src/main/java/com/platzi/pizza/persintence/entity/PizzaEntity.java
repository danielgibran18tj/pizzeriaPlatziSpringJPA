package com.platzi.pizza.persintence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor
public class PizzaEntity {
    @Id     //indica que es nuestra clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //genere valor automatico cada que ingrese una pizza
    @Column(name = "id_pizza")
    private Integer idPizza;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false, columnDefinition = "Decimal(5,2)")
    private Double price;

    @Column(columnDefinition = "boolean")  // para mysql es "tinyint"
    private Boolean vegetarian;

    @Column(columnDefinition = "boolean")
    private Boolean vegan;

    @Column(columnDefinition = "boolean", nullable = false)
    private Boolean available;
}
