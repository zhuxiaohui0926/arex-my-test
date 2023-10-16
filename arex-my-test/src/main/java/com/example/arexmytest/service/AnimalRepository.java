package com.example.arexmytest.service;

import com.example.arexmytest.pojo.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {
}
