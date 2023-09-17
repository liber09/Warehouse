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

}
