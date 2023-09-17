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
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10);
        warehouse.addProduct("RayBan sunglasses",Category.ACCESSORIES,8);
        var allProducts = warehouse.getAllProducts();
        assertFalse(allProducts.isEmpty());
        assertEquals(3,allProducts.size());
    }

    @Test
    void getProductByIdProductFound(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10);
        warehouse.addProduct("RayBan sunglasses",Category.ACCESSORIES,8);
        var productWrapper = warehouse.getProductById(2);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein skinny jeans", product.getName()));
    }
    @Test
    void getProductByIdProductNotFound(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        warehouse.addProduct("Calvin Klein skinny jeans",Category.JEANS,10);
        warehouse.addProduct("RayBan sunglasses",Category.ACCESSORIES,8);
        var product = warehouse.getProductById(4);
        assertTrue(product.isEmpty());
    }

    @Test
    void modifyProductNameSuccess(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.TSHIRTS,5);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Calvin Klein tshirt", product.getName()));

    }
    @Test
    void modifyProductNameFails(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"",Category.TSHIRTS,5);
        assertFalse(result);
    }
    @Test
    void modifyProductCategorySuccess(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.TSHIRTS, product.getCategory()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,5);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(Category.LONGSLEVE, product.getCategory()));

    }
    @Test
    void modifyProductCategoryFails(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals("Diesel tshirt", product.getName()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",null,5);
        assertFalse(result);
    }

    @Test
    void modifyProductRatingSuccess(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Calvin Klein tshirt",Category.LONGSLEVE,6);
        assertTrue(result);
        productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(6, product.getRating()));

    }
    @Test
    void modifyProductRatingTooHighFails(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,11);
        assertFalse(result);
    }
    @Test
    void modifyProductRatingNegativeNumberFails(){
        warehouse.addProduct("Diesel tshirt",Category.TSHIRTS,5);
        var productWrapper = warehouse.getProductById(1);
        productWrapper.ifPresent(product -> assertEquals(5, product.getRating()));
        var result = warehouse.modifyProduct(1,"Diesel tshirt",Category.TSHIRTS,-1);
        assertFalse(result);
    }

}
