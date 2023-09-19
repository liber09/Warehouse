package service;

import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    public Warehouse(){

    }
    private final ArrayList<Product> products = new ArrayList<>();

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

        return true;
    }

    public List<Product> getAllProducts(){
        return products.stream().toList();
    }

    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Product> getAllProductsInCategory(Category category){
        return products.stream().filter(p -> p.getCategory().equals(category)).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
    }

    public List<Product> getAllProductsCreatedSince(LocalDate createdDate){
        return products.stream().filter(p -> p.getCreatedDate().isAfter(createdDate)).collect(Collectors.toList());
    }

    public List<Product> getAllModifiedProducts(){
        return products.stream().filter(p -> !p.getModifiedDate().isEqual(p.getCreatedDate())).collect(Collectors.toList());
    }

    public List<Category> getAllCategoriesWithOneOrMoreProducts(){
        List<Category> categoriesWithProducts = new ArrayList<>();
        List<Product> tempProducts;
        for (Category category : EnumSet.allOf(Category.class)) {
            tempProducts = getAllProductsInCategory(category);
            if (!tempProducts.isEmpty()) {
                categoriesWithProducts.add(category);
            }
            tempProducts.clear();
        }
        return categoriesWithProducts;
    }

    public long getNumberOfProductsInCategory(Category category){
        return products.stream().filter(p ->p.getCategory().equals(category)).count();
    }

    public List<Product> getProductsWithMaxRatingSortedByDate(){
        int maxRating = 10;
        return products.stream()
                .filter(p -> p.getRating().equals(maxRating) &&
                        p.getCreatedDate().getMonth().equals(LocalDate.now().getMonth()))
                .sorted(Comparator.comparing(Product::getCreatedDate))
                .collect(Collectors.toList());
    }

    public boolean modifyProduct(int id, String name, Category category, int rating) {
        Optional<Product> productWrapper = getProductById(id);

        if (productWrapper.isEmpty()) {
            return false;
        } else {
            LocalDate modifiedDate = LocalDate.now();
            Product product = productWrapper.get();
            if (!name.isEmpty()){
                product.setName(name);
                product.setModifedDate(modifiedDate);
            } else {
                System.out.println("Product must have a name");
                return false;
            }

            if (!(category == null)){
                product.setCategory(category);
                product.setModifedDate(modifiedDate);
            } else {
                System.out.println("Product must belong to a category");
                return false;
            }

            if (rating >= 0 && rating <= 10){
                product.setRating(rating);
                product.setModifedDate(modifiedDate);
            } else{
                System.out.println("Rating must be between 0 and 10");
                return false;
            }
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
        System.out.println(firstLetterCount);
        return firstLetterCount;
    }
}
