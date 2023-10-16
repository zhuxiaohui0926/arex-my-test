package com.example.arexmytest.service.impl;

import com.example.arexmytest.pojo.Animal;
import com.example.arexmytest.service.AnimalRepository;
import com.example.arexmytest.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Animal getAnimalById(Integer id) {
         Animal animal = animalRepository.findById(id).orElse(null);
         return animal;
    }

    @Override
    public List<Animal> getAllAnimal() {
        Iterable<Animal> iterable = animalRepository.findAll();
        List<Animal> animals = new ArrayList<>();
        iterable.forEach(animal -> animals.add(animal));
        return animals;
    }

    @Override
    public Integer addAnimal(Animal animal) {
         return animalRepository.save(animal).getId();
    }

    @Override
    public Animal updateAnimal(Animal animal) {
        Animal animalDTO = animalRepository.findById(animal.getId()).orElse(null);
        if(animalDTO != null){
            if(animal.getAge() != null){
                animalDTO.setAge(animal.getAge());
            }
            if(animal.getName() != null){
                animalDTO.setName(animal.getName());
            }
        }
        return animalRepository.save(animalDTO);
    }
}
