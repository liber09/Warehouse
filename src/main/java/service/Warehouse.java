package service;

import entities.Category;
import entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Warehouse {

    public Warehouse(){

    }
    private final ArrayList<Product> products = new ArrayList<>();

    public boolean addProduct(String name, Category category, int rating) {
        if(name.trim().isEmpty()){
            System.out.println("Can't add products without name");

            return false;
        }

        if (category == null){
            category = Category.OTHER;
        }

        Product newProduct = new Product(name,category,rating);
        products.add(newProduct);

        return true;
    }

    public List<Product> getAllProducts(){
        return products.stream().toList();
    }

    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }

    public boolean modifyProduct(int id, String name, Category category, int rating) {
        Optional<Product> productWrapper = getProductById(id);

        if (productWrapper.isEmpty()) {
            return false;
        } else {
            Product product = productWrapper.get();
            if (!name.isEmpty()){
                product.setName(name);
            } else {
                System.out.println("Product must have a name");
            }

            if (!(category == null)){
                product.setCategory(category);
            } else {
                System.out.println("Product must belong to a category");
            }

            if (rating >= 0 && rating <= 10){
                product.setRating(rating);
            } else{
                System.out.println("Rating must be between 0 and 10");
            }
        }

        return true;
    }
}
