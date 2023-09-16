package service;

import entities.Category;
import entities.Product;

public class Warehouse {
    public Product AddProduct(String name, Category category, int rating){
        if(name.trim().isEmpty()){
            System.out.println("Can not add products without name");
            return null;
        }
        if (category == null){
            category = Category.OTHER;
        }
        return new Product(name,category,rating);
    }
}
