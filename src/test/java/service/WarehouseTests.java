package service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

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

}
