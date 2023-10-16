package com.example.arexmytest.controller;

import com.example.arexmytest.pojo.Animal;
import com.example.arexmytest.pojo.TradeMocker;
import com.example.arexmytest.service.AnimalService;
import com.example.arexmytest.service.impl.MongoDbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/test")
public class MyTestController {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private MongoDbServiceImpl mongoDbService;
    @GetMapping("/healthCheck")
    public String healthCheck(String str1){
        return "ok" + str1;
    }

    @GetMapping("/queryMongoDb")
    public List<TradeMocker> queryMongoDb(@PathParam("recordId") String recordId){
        return mongoDbService.queryByID(recordId);
    }

   @PostMapping("/dataBaseAddTest")
    public Integer dataBaseAddTest(@RequestBody Animal animal){
       return animalService.addAnimal(animal);
    }

    @PostMapping("/dataBaseQueryTest")
    public List<Animal> dataBaseQueryTest(){
        List<Animal> animals = animalService.getAllAnimal();
        return animals;
    }
    @PostMapping("/dataBaseUpdateTest")
    public Animal dataBaseUpdateTest(@RequestBody Animal animal){
        Animal result = animalService.updateAnimal(animal);
        System.out.println(System.currentTimeMillis());
        System.out.println("update success:" + result);
        return result;
    }
}
