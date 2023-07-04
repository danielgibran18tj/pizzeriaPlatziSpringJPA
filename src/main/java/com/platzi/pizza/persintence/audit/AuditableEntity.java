package com.platzi.pizza.persintence.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@MappedSuperclass
public class AuditableEntity {
    //nuevas columnas para nuestra auditoria

    @Column(name = "created_date")
    @CreatedDate
    //@CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "create_by")
    @CreatedBy
    private String createBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    public void hacerSonido() {
        System.out.println("Haciendo sonido...");
    }
}
