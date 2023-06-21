package com.platzi.pizza.persintence.repository;

import com.platzi.pizza.persintence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
