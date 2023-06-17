package com.platzi.pizza.persintence.entity;

import com.platzi.pizza.persintence.audit.AuditPizzaListener;
import com.platzi.pizza.persintence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Table(name = "pizza")
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class}) // por ser 2 se ponen entre llaves
@Getter
@Setter
@NoArgsConstructor
public class PizzaEntity extends AuditableEntity implements Serializable {
    @Id    //indica que es nuestra clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //genere valor automatico cada que ingrese una pizza
    @Column(name = "id_pizza", nullable = false)
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

    @Override
    public String toString() {
        return "PizzaEntity{" +
                "idPizza=" + idPizza +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", available=" + available +
                '}';
    }
}
