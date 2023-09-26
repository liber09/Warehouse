package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductRecord;

import java.time.LocalDate;
import java.util.*;

public class Warehouse {
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<ProductRecord> productRecords = createRecords();

    public boolean addProduct(String name, Category category, int rating, LocalDate creationDate) {
        if(name.trim().isEmpty()){
            System.out.println("Can't add products without name");
            return false;
        }

        if (category == null){
            category = Category.OTHER;
        }

        int id = products.size()+1;
        Product newProduct = new Product(id,name,category,rating, creationDate);

        products.add(newProduct);
        createRecords();
        productRecords.add(new ProductRecord(newProduct.getId(),newProduct.getName(),newProduct.getCategory(),newProduct.getRating(),newProduct.getCreatedDate(),newProduct.getModifiedDate()));

        return true;
    }

    public List<ProductRecord> getAllProducts(){
        createRecords();
        return productRecords.stream().toList();
    }

    public Optional<ProductRecord> getProductRecordById(int id) {
        createRecords();
        return productRecords.stream()
                .filter(p -> p.id() == id).findFirst();
    }

    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<ProductRecord> getAllProductsInCategory(Category category){
        createRecords();
        return productRecords.stream().filter(p -> p.category().equals(category)).sorted(Comparator.comparing(ProductRecord::name)).toList();
    }

    public List<ProductRecord> getAllProductsCreatedSince(LocalDate createdDate){
        createRecords();
        return productRecords.stream().filter(p -> p.creationDate().isAfter(createdDate)).toList();
    }

    public List<ProductRecord> getAllModifiedProducts(){
        productRecords.clear();
        createRecords();
        return productRecords.stream().filter(p -> !p.modifiedDate().isEqual(p.creationDate())).toList();
    }

    public List<Category> getAllCategoriesWithOneOrMoreProducts(){
        createRecords();
        List<Category> categoriesWithProducts = new ArrayList<>();
        List<ProductRecord> tempProducts;
        for (Category category : EnumSet.allOf(Category.class)) {
            tempProducts = getAllProductsInCategory(category);
            if (!tempProducts.isEmpty()) {
                categoriesWithProducts.add(category);
            }
        }
        return categoriesWithProducts;
    }

    public long getNumberOfProductsInCategory(Category category){
        return products.stream().filter(p ->p.getCategory().equals(category)).count();
    }

    public List<ProductRecord> getProductsWithMaxRatingSortedByDate(){
        createRecords();
        int maxRating = 10;
        return productRecords.stream()
                .filter(p -> p.rating() == maxRating &&
                        p.creationDate().getMonth().equals(LocalDate.now().getMonth()))
                .sorted(Comparator.comparing(ProductRecord::creationDate))
                .toList();
    }

    public boolean modifyProduct(int id, String name, Category category, int rating) {
        createRecords();
        Optional<Product> productWrapper = getProductById(id);
        if (productWrapper.isEmpty()) {
            return false;
        } else {
            LocalDate modifiedDate = LocalDate.now();
            Product product = productWrapper.get();
            if (!name.isEmpty()){
                product.setName(name);
                product.setModifiedDate(modifiedDate);
            } else {
                System.out.println("Product must have a name");
                return false;
            }

            if (!(category == null)){
                product.setCategory(category);
                product.setModifiedDate(modifiedDate);
            } else {
                System.out.println("Product must belong to a category");
                return false;
            }

            if (rating >= 0 && rating <= 10){
                product.setRating(rating);
                product.setModifiedDate(modifiedDate);
            } else{
                System.out.println("Rating must be between 0 and 10");
                return false;
            }
            productRecords.clear();
            createRecords();
        }

        return true;
    }

    public Map<String,Integer> getProductLetterAndProductCount(){
        Map<String,Integer> firstLetterCount = new HashMap<>();
        for (Product product : products) {
            String tempProductFirstLetter = product.getName().substring(0, 1);
            if (!firstLetterCount.containsKey(tempProductFirstLetter)) {
                firstLetterCount.put(tempProductFirstLetter, 1);
            } else {
                firstLetterCount.put(tempProductFirstLetter, firstLetterCount.get(tempProductFirstLetter) + 1);
            }
        }
        return firstLetterCount;
    }

    private ArrayList<ProductRecord> createRecords(){
        ArrayList<ProductRecord> productRecordList = new ArrayList<>();
        for (Product tempProduct : products) {
            productRecordList.add(new ProductRecord(tempProduct.getId(), tempProduct.getName(), tempProduct.getCategory(), tempProduct.getRating(), tempProduct.getCreatedDate(), tempProduct.getModifiedDate()));
        }
        return productRecordList;
    }
}
