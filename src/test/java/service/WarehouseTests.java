package service;

import entities.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTests {
    private final Warehouse warehouse = new Warehouse();

    @Test
    void addProductSuccess(){
        var productAddedOk = warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        assertTrue(productAddedOk);
    }

    @Test
    void addProductFails(){
        var productAddedOk = warehouse.addProduct("",Category.TSHIRTS,5);
        assertFalse(productAddedOk);
    }

    @Test
    void getAllProducts(){
        setupTestProducts();
        var allProducts = warehouse.getAllProducts();
        assertFalse(allProducts.isEmpty());
        assertEquals(9,allProducts.size());
    }

    @Test
    void getProductByIdProductFound(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(9);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein skinny jeans", product.getName()));
    }
    @Test
    void getProductByIdProductNotFound(){
        setupTestProducts();
        var product = warehouse.getProductById(10);
        assertTrue(product.isEmpty());
    }

    @Test
    void modifyProductNameSuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.TSHIRTS,5);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein tshirt", product.getName()));

    }
    @Test
    void modifyProductNameFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"",Category.TSHIRTS,5);
        assertFalse(result);
    }
    @Test
    void modifyProductCategorySuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.TSHIRTS, product.getCategory()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,5);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.LONGSLEVE, product.getCategory()));

    }
    @Test
    void modifyProductCategoryFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",null,5);
        assertFalse(result);
    }

    @Test
    void modifyProductRatingSuccess(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,6);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(6, product.getRating()));

    }
    @Test
    void modifyProductRatingTooHighFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,11);
        assertFalse(result);
    }
    @Test
    void modifyProductRatingNegativeNumberFails(){
        setupTestProducts();
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,-1);
        assertFalse(result);
    }

    @Test
    void getAllProductsInCategorySortedAlphabetically(){
        setupTestProducts();
        var allTshirts = warehouse.getAllProductsInCategory(Category.TSHIRTS);
        assertFalse(allTshirts.isEmpty());
        assertEquals(3,allTshirts.size());
    }

    private void setupTestProducts(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        warehouse.addProduct("Calvin Klein tshirt",Category.TSHIRTS,7);
        warehouse.addProduct("Alpha industries tshirt",Category.TSHIRTS,7);
        warehouse.addProduct("H&M jeans",Category.JEANS,3);
        warehouse.addProduct("Softpants",Category.PANTS,4);
        warehouse.addProduct("Rayban sunglasses",Category.ACCESSORIES,5);
        warehouse.addProduct("Adidas cap",Category.HATS,5);
        warehouse.addProduct("Nike cap",Category.HATS,6);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10);

    }

}
