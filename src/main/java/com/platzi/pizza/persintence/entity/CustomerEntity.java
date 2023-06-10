package com.platzi.pizza.persintence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {   //datos del cliente
    @Id     //indica que es nuestra clave primaria
    //@GeneratedValue(strategy = GenerationType.AUTO)   //nosostros ingresaremos la ID del cliente
    @Column(name = "id_customer", nullable = false, length = 15)
    private String idCustomer;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 50, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20, unique = true)
    private String phoneNumber;

}
