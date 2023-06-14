package com.platzi.pizza.service.dto;

import lombok.Data;

@Data //crea los getter , setter, constructores, etc
public class UpdatePizzaPriceDto {
    private int pizzaId;
    private double newPrice;

}
