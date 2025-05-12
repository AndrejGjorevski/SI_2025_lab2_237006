package com.exercise;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {

    @Test
    void checkCartEveryStatement() {
        //Тест 1: Сите податоци се валидни
        List<Item> validList = List.of(new Item("Eggs", 2, 100, 0));
        String validCard = "0123456789012345";
        double expected = 200.0;
        assertEquals(expected, SILab2.checkCart(validList, validCard));

        //Тест 2: Листата е null
        List<Item> nullList = null;
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(nullList, validCard));
        assertEquals("allItems list can't be null!", ex.getMessage());

        //Тест 3: Некој Item во листата нема име
        List<Item> noNameItemList = List.of(new Item("", 2, 100, 0));
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(noNameItemList, validCard));
        assertEquals("Invalid item!", ex.getMessage());

        //Тест 4: Item со попуст, но cardNumber e null
        List<Item> discountItemList = List.of(new Item("Eggs", 2, 100, 0.3));
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(discountItemList, null));
        assertEquals("Invalid card number!", ex.getMessage());

        //Тест 5: Валидни Items, но cardNumber содржи невалидни карактери
        String invalidCard = "012345abc9012345";
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(validList, invalidCard));
        assertEquals("Invalid character in card number!", ex.getMessage());
    }

    @Test
    void checkCartMultipleCondition() {
        String validCard = "0123456789012345";

        // Test 1: All true (Price > 300, Discount > 0, Quantity > 10)
        assertEquals(350 * (1 - 0.3) * 12 - 30, SILab2.checkCart(List.of(new Item("Eggs", 12, 350, 0.3)), validCard), 0.001);

        // Test 2: Price and Discount true
        assertEquals(350 * (1 - 0.3) * 7 - 30, SILab2.checkCart(List.of(new Item("Eggs", 7, 350, 0.3)), validCard), 0.001);

        // Test 3: Quantity and Price true
        assertEquals(350 * 12 - 30, SILab2.checkCart(List.of(new Item("Eggs", 12, 350, 0)), validCard), 0.001);

        // Test 4: Only Price true
        assertEquals(350 * 7 - 30, SILab2.checkCart(List.of(new Item("Eggs", 7, 350, 0)), validCard), 0.001);

        // Test 5: Quantity and Discount true
        assertEquals(100 * (1 - 0.2) * 12 - 30, SILab2.checkCart(List.of(new Item("Eggs", 12, 100, 0.2)), validCard), 0.001);

        // Test 6: Only Discount true
        assertEquals(100 * (1 - 0.2) * 9 - 30, SILab2.checkCart(List.of(new Item("Eggs", 9, 100, 0.2)), validCard), 0.001);

        // Test 7: Only Quantity true
        assertEquals(100 * 12 - 30, SILab2.checkCart(List.of(new Item("Eggs", 12, 100, 0)), validCard), 0.001);

        // Test 8: None true
        assertEquals(100 * 7, SILab2.checkCart(List.of(new Item("Eggs", 7, 100, 0)), validCard), 0.001);
    }
}