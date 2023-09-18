package entities;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private final int id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDate createdDate;
    private LocalDate modifiedDate;

    public Product(int id, String name, Category category, int rating, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = creationDate;
        this.modifiedDate = createdDate;

    }

    public Integer getId(){
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

    public void setModifedDate(LocalDate value){
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

    public int getRating(){
        return this.rating;
    }

    public LocalDate getCreatedDate(){
        return this.createdDate;
    }
}
