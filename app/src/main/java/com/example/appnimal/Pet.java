package com.example.appnimal;

public class Pet {

    public Pet(){
    }

    public Pet(String name, String species, String breed, int age) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() { return breed;
    }


    public int getAge() {
        return age;
    }


    private String name;
    private String species;
    private String breed;
    private int age;
}
