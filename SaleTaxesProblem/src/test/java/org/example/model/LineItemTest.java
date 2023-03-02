package org.example.model;

import org.example.parser.InputParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LineItemTest {
    LineItem lineItem = new LineItem("box of chocolates",1,32.72f,true,false);


    @Test
    void ShouldReturnFinalCostUsingSettersAndGetters() {
        LineItem lineItem = new LineItem();
        float expected = 32.05f;

        lineItem.setPriceIncludingTax(expected);
        float actual = lineItem.getPriceIncludingTax();

        assertEquals(expected,actual);
    }

    @Test
    void ShouldReturnNameUsingConstructor() {
        String expected = "box of chocolates";

        String actual = lineItem.getName();

        assertEquals(expected,actual);
    }

    @Test
    void ShouldReturnQuantityUsingConstructor() {
        int expected = 1;

        int actual = lineItem.getQuantity();

        assertEquals(expected,actual);
    }

    @Test
    void ShouldReturnInitialCostUsingConstructor() {
        float expected = 32.72f;

        float actual = lineItem.getPrice();

        assertEquals(expected,actual);
    }

    @Test
    void shouldReturnTrueIfExemptedUsingConstructor() {
        boolean expected = true;

        boolean actual = lineItem.isExempted();

        assertEquals(expected,actual);
    }

    @Test
    void shouldReturnTrueIfImportedUsingConstructor() {
        boolean expected = false;

        boolean actual = lineItem.isImported();

        assertEquals(expected,actual);
    }

    @Test
    void ShouldReturnItemList() {
        ArrayList<LineItem> expectedList = new ArrayList<LineItem>();
        expectedList.add(new LineItem("chocolate bar",2,82.65f,true,true));
        expectedList.add(new LineItem("bottle of perfume",1, 27.45f,false,false));
        ArrayList<InputParser> list = new ArrayList<InputParser>();
        list.add(new InputParser(2,82.65f,true,"chocolate bar"));
        list.add(new InputParser(1,27.45f,false,"bottle of perfume"));

        ArrayList<LineItem> actualList=new LineItem().defineItem(list);

        assertArrayEquals(expectedList.toArray(),actualList.toArray());
    }
}