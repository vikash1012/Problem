package org.example.model;

import org.example.parser.InputParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExemptedItemsTest {

    @Test
    void ShouldReturnTrueIfExempted() {
        ExemptedItems item = new ExemptedItems();
        String input = "bar of chocolate";
        boolean expected = true;

        boolean actual = item.isExempted(input);

        assertEquals(expected,actual);
    }

    @Test
    void ShouldReturnFalseIfNotExempted() {
        ExemptedItems item = new ExemptedItems();
        String input = "bottle of Perfume";
        boolean expected = false;

        boolean actual = item.isExempted(input);

        assertEquals(expected,actual);
    }


}