package org.example.entities;

import org.example.entities.helpers.FakeUuidProvider;
import org.example.entities.helpers.RandomUuidProvider;
import org.example.interfaces.UuidProvider;

import java.time.LocalDate;
import java.util.UUID;

public class Product {
    private final UUID id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDate createdDate;
    private LocalDate modifiedDate;

    public Product(String name, Category category, int rating, LocalDate creationDate, Boolean isTest, int testId) {
        if(!isTest){
            UuidProvider uuidProvider = new RandomUuidProvider();
            this.id = uuidProvider.uuid();
        }else{
            UuidProvider uuidProviderTest = new FakeUuidProvider();
            this.id = uuidProviderTest.genterateUuid(testId);
        }

        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = creationDate;
        this.modifiedDate = createdDate;
    }

    public UUID getId(){
        return this.id;
    }

    public void setName(String value){
        if(!value.isEmpty()){
            this.name = value;
        }
    }

    public void setCategory(Category value){
        if(!(value == null)){
            this.category = value;
        }
    }

    public void setRating(int value){
        if (value >= 0 && value <=10){
            this.rating = value;
        }
    }

    public void setModifiedDate(LocalDate value){
        if (value.isAfter(this.createdDate)){
            this.modifiedDate = value;
        } else {
            System.out.println("Modified date cannot be before creation date");
        }
    }

    public String getName(){
        return this.name;
    }

    public Category getCategory(){
        return this.category;
    }

    public Integer getRating(){
        return this.rating;
    }

    public LocalDate getCreatedDate(){
        return this.createdDate;
    }

    public LocalDate getModifiedDate(){
        return this.modifiedDate;
    }
}
