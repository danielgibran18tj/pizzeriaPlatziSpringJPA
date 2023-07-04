package com.platzi.pizza.persintence.audit;

import com.platzi.pizza.persintence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

import java.util.Objects;
//MUY IMPORTANTE QUE ESTOS PROCESOS FUNCIONES SOLO CON LOS PROCESOS CRUD DE SPRING DATA REPOSITORY, NO FUNCIONA CON QUERY NATIVO
public class AuditPizzaListener {   //auditoria al actualizar, eliminar y crear
    private PizzaEntity currentValue;

    @PostLoad       //audita desps que ocurra un SELECT, y la info sea cargada a un entity
    public void postLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);   // tomar valor de entity que acaba de cargar
    }

    @PostPersist    //audita desps de crear la pizza
    @PostUpdate     //audita desps de actualizar la pizza
    public void onPostPersist(PizzaEntity entity){
        System.out.println("POST PERSIST OR UPDATE");
        if (currentValue != null){
            if (Objects.equals(this.currentValue.getIdPizza(), entity.getIdPizza())) {
                System.out.println("OLD VALUE: " + this.currentValue.toString());   //imprimir valores viejos de mi pizza
            }
        }
        System.out.println("NEW VALUE: " + entity.toString());  //imprimir nuevos valores de mi pizza
    }

    @PreRemove      //audita antes de eliminar la pizza
    public void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}
