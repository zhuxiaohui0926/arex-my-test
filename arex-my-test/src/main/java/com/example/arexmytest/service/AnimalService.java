package com.example.arexmytest.service;

import com.example.arexmytest.pojo.Animal;

import java.util.List;

public interface AnimalService {
    Animal getAnimalById(Integer id);
    public List<Animal> getAllAnimal();
    Integer addAnimal(Animal animal);
    Animal updateAnimal(Animal animal);
}
