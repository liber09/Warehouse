package entities;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private Category category;
    private int rating;
    private final Date createdDate;
    private Date modifiedDate;

    public Product(String name, Category category, int rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = new Date();
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
}