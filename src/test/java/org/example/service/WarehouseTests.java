package org.example.service;

import org.example.entities.Category;
import org.example.service.Warehouse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTests {
    private final Warehouse warehouse = new Warehouse();

    @Test
    void addProductSuccess(){
        var productAddedOk = warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5,LocalDate.now());
        assertTrue(productAddedOk);
    }

    @Test
    void addProductFails(){
        var productAddedOk = warehouse.addProduct("",Category.TSHIRTS,5,LocalDate.now());
        assertFalse(productAddedOk);
    }

    @Test
    void getAllProducts(){
        setupTestProducts();
        var allProducts = warehouse.getAllProducts();
        assertFalse(allProducts.isEmpty());
        assertEquals(11,allProducts.size());
    }

    @Test
    void getProductByIdProductFound(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(10);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein skinny jeans", product.name()));
    }
    @Test
    void getProductByIdProductNotFound(){
        setupTestProducts();
        var product = warehouse.getProductRecordById(12);
        assertTrue(product.isEmpty());
    }

    @Test
    void modifyProductNameSuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.name()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.TSHIRTS,5);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein tshirt", product.name()));

    }
    @Test
    void modifyProductNameFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.name()));
        var result = warehouse.modifyProduct(1,"",Category.TSHIRTS,5);
        assertFalse(result);
    }
    @Test
    void modifyProductCategorySuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.TSHIRTS, product.category()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,5);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.LONGSLEVE, product.category()));

    }
    @Test
    void modifyProductCategoryFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.name()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",null,5);
        assertFalse(result);
    }

    @Test
    void modifyProductRatingSuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,6);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(6, product.rating()));

    }
    @Test
    void modifyProductRatingTooHighFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,11);
        assertFalse(result);
    }
    @Test
    void modifyProductRatingNegativeNumberFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,-1);
        assertFalse(result);
    }

    @Test
    void getAllProductsInCategorySortedAlphabetically(){
        setupTestProducts();
        var allShirts = warehouse.getAllProductsInCategory(Category.TSHIRTS);
        assertFalse(allShirts.isEmpty());
        assertEquals(4,allShirts.size());
    }

    @Test
    void getAllProductsCreatedSinceDateProductsFound() {
        setupTestProducts();
        warehouse.addProduct("Cool sunglasses", Category.ACCESSORIES, 7, LocalDate.now());
        warehouse.addProduct("Metallica longsleve", Category.LONGSLEVE, 6, LocalDate.now());
        LocalDate lastDate = LocalDate.of(2023, Month.SEPTEMBER, 15);
        var newProducts = warehouse.getAllProductsCreatedSince(lastDate);
        assertFalse(newProducts.isEmpty());
        assertEquals(2,newProducts.size());
    }

    @Test
    void getAllProductsCreatedSinceDateProductsNotFound() {
        setupTestProducts();
        var newProducts = warehouse.getAllProductsCreatedSince(LocalDate.now());
        assertTrue(newProducts.isEmpty());
    }

    @Test
    void getAllModifiedProducts(){
        setupTestProducts();
        warehouse.modifyProduct(5,"Pink softpants",Category.PANTS,4);
        warehouse.modifyProduct(8,"Nike cap",Category.HATS,8);
        warehouse.modifyProduct(1,"Diesel shirt",Category.LONGSLEVE,5);
        var modifiedProducts = warehouse.getAllModifiedProducts();
        assertFalse(modifiedProducts.isEmpty());
        assertEquals(3,modifiedProducts.size());
    }

    @Test
    void getAllCategoriesWithProducts(){
        setupTestProducts();
        var categoriesWithProducts = warehouse.getAllCategoriesWithOneOrMoreProducts();
        assertFalse(categoriesWithProducts.isEmpty());
        assertEquals(6,categoriesWithProducts.size());
    }

    @Test
    void getNumberOfProductsInCategory(){
        setupTestProducts();
        var productCount = warehouse.getNumberOfProductsInCategory(Category.JEANS);
        assertEquals(2,productCount);
    }

    @Test
    void getProductsWithMaxRatingCreatedCurrentMonth(){
        setupTestProducts();
        var latestProductsWithMaxRating = warehouse.getProductsWithMaxRatingSortedByDate();
        assertFalse(latestProductsWithMaxRating.isEmpty());
        assertEquals(2, latestProductsWithMaxRating.size());
    }

    @Test
    void getMapWithFirstLetterAndProductCount(){
        setupTestProducts();
        var firstLetterAndProductCountMap = warehouse.getProductLetterAndProductCount();
        assertFalse(firstLetterAndProductCountMap.isEmpty());
        assertEquals(2,firstLetterAndProductCountMap.get("A"));
        assertEquals(1,firstLetterAndProductCountMap.get("R"));
        assertEquals(2,firstLetterAndProductCountMap.get("C"));
        assertEquals(1,firstLetterAndProductCountMap.get("S"));
        assertEquals(1,firstLetterAndProductCountMap.get("D"));
        assertEquals(2,firstLetterAndProductCountMap.get("H"));
        assertEquals(2,firstLetterAndProductCountMap.get("N"));
    }


    private void setupTestProducts(){
        LocalDate createdDate = LocalDate.of(2023, LocalDate.now().getMonth(), 15);
        LocalDate dateLastMonth = LocalDate.of(2023, Month.AUGUST, 15);

        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5,createdDate);
        warehouse.addProduct("Calvin Klein tshirt",Category.TSHIRTS,7,createdDate);
        warehouse.addProduct("Alpha industries tshirt",Category.TSHIRTS,7,createdDate);
        warehouse.addProduct("H&M jeans",Category.JEANS,5,createdDate);
        warehouse.addProduct("Softpants",Category.PANTS,4,createdDate);
        warehouse.addProduct("Rayban sunglasses",Category.ACCESSORIES,5,createdDate);
        warehouse.addProduct("Adidas cap",Category.HATS,5,createdDate);
        warehouse.addProduct("Nike cap",Category.HATS,6,createdDate);
        warehouse.addProduct("Hugo Boss tshirt",Category.TSHIRTS,10,createdDate);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10,createdDate);
        warehouse.addProduct("Nike shoes", Category.SHOES, 10, dateLastMonth);
    }
}
