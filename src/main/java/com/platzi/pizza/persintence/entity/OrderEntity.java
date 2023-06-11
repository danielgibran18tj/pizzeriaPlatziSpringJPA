package com.platzi.pizza.persintence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //genere valor automatico cada que ingrese una pizza
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;

    @Column(name = "id_customer", nullable = false, length = 15)
    private String idCustomer;

    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "DECIMAL(6,2)")
    private Double total;

    @Column(nullable = false, columnDefinition = "char(1)")
    private String method;

    @Column(name = "additional_notes", length = 200)
    private String additionalNotes;

    //ANOTACIONES CON LAS QUE HAY QUE ESTAR PENDIENTES EN LAS RELACIONES
    @OneToOne(fetch = FetchType.LAZY)   //no cargue esta relacion, hasta que en verdad se use
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer", insertable = false, updatable = false )
    @JsonIgnore
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItemEntity> items;

}
