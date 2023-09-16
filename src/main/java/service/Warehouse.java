package service;

import entities.Category;
import entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Warehouse {
    private List<Product> products = new ArrayList<>();
    public boolean AddProduct(String name, Category category, int rating) {
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
    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }

    public boolean modifyProduct(int id, String name, Category category, int rating) {
        Optional<Product> product = getProductById(id);

        if (!product.isPresent()) {
            return false;
        } else {
            if (!name.isEmpty()){

            }
        }

        return true;
    }
}
