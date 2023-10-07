package org.example.service;

import org.example.entities.Category;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTests {
    private final Warehouse warehouse = new Warehouse();

    @Test
    void addProductSuccess(){
        var productAddedOk = warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5,LocalDate.now(),true,1);
        assertTrue(productAddedOk);
    }

    @Test
    void addProductFails(){
        var productAddedOk = warehouse.addProduct("",Category.TSHIRTS,5,LocalDate.now(), true, 1);
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
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-000010"));
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein skinny jeans", product.name()));
    }
    @Test
    void getProductByIdProductNotFound(){
        setupTestProducts();
        var product = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-000012"));
        assertTrue(product.isEmpty());
    }

    @Test
    void modifyProductNameSuccess() throws Exception {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.name()));
        var result = warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Calvin Klein tshirt",Category.TSHIRTS,5);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein tshirt", product.name()));

    }
    @Test
    void modifyProductNameFails() {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.name()));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"",Category.TSHIRTS,5));
        assertTrue(ex.getMessage().contains("Product must have a name"));
    }
    @Test
    void modifyProductCategorySuccess() throws Exception {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(Category.TSHIRTS, product.category()));
        var result = warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Calvin Klein tshirt",Category.LONGSLEVE,5);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(Category.LONGSLEVE, product.category()));

    }

    @Test
    void modifyProductNoProductToModify() {
        setupTestProducts();
        Exception ex = assertThrows(Exception.class, () -> warehouse.modifyProduct(UUID.fromString("0000-00-00-00-000025"),"Diesel tshirt",null,5));
        assertTrue(ex.getMessage().contains("No product to modify"));

    }

    @Test
    void modifyProductCategoryFails() {
        setupTestProducts();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Diesel tshirt",null,5));
        assertTrue(ex.getMessage().contains("Product must belong to a category"));
    }

    @Test
    void modifyProductRatingSuccess() throws Exception {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));
        var result = warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Calvin Klein tshirt",Category.LONGSLEVE,6);
        assertTrue(result);
        productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(6, product.rating()));
    }
    @Test
    void modifyProductRatingTooHighFails() {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Diesel tshirt",Category.TSHIRTS,11));
        assertTrue(ex.getMessage().contains("Rating must be between 0 and 10"));
    }
    @Test
    void modifyProductRatingNegativeNumberFails() {
        setupTestProducts();
        var productWrapper = warehouse.getProductRecordById(UUID.fromString("0000-00-00-00-00001"));
        productWrapper.ifPresent(product -> assertEquals(5, product.rating()));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Diesel tshirt",Category.TSHIRTS,-1));
        assertTrue(ex.getMessage().contains("Rating must be between 0 and 10"));
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
        warehouse.addProduct("Cool sunglasses", Category.ACCESSORIES, 7, LocalDate.now(), true, 12);
        warehouse.addProduct("Metallica longsleve", Category.LONGSLEVE, 6, LocalDate.now(), true, 13);
        LocalDate lastDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).minusDays(7);
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
    void getAllModifiedProducts() throws Exception {
        setupTestProducts();
        warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00005"),"Pink softpants",Category.PANTS,4);
        warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00008"),"Nike cap",Category.HATS,8);
        warehouse.modifyProduct(UUID.fromString("0000-00-00-00-00001"),"Diesel shirt",Category.LONGSLEVE,5);
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
        LocalDate createdDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        warehouse.addProduct("Cool sunglasses", Category.ACCESSORIES, 10, createdDate, true, 12);
        warehouse.addProduct("Metallica longsleve", Category.LONGSLEVE, 10, createdDate, true,13);
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
        LocalDate dateLastMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), LocalDate.now().getDayOfMonth());

        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5,dateLastMonth, true, 1);
        warehouse.addProduct("Calvin Klein tshirt",Category.TSHIRTS,7,dateLastMonth, true, 2);
        warehouse.addProduct("Alpha industries tshirt",Category.TSHIRTS,7,dateLastMonth, true, 3);
        warehouse.addProduct("H&M jeans",Category.JEANS,5,dateLastMonth, true, 4);
        warehouse.addProduct("Softpants",Category.PANTS,4,dateLastMonth, true, 5);
        warehouse.addProduct("Rayban sunglasses",Category.ACCESSORIES,5,dateLastMonth, true, 6);
        warehouse.addProduct("Adidas cap",Category.HATS,5,dateLastMonth, true, 7);
        warehouse.addProduct("Nike cap",Category.HATS,6,dateLastMonth, true,8);
        warehouse.addProduct("Hugo Boss tshirt",Category.TSHIRTS,10,dateLastMonth, true,9);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10,dateLastMonth, true,10);
        warehouse.addProduct("Nike shoes", Category.SHOES, 10, dateLastMonth, true,11);
    }
}
