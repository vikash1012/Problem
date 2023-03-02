package org.example.parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {



    @Test
    void getQuantity() {
        InputParser input = new InputParser(2, 15,false,"box of pills");
        int expected = 2;

        int actual = input.getQuantity();

        assertEquals(expected,actual);
    }

    @Test
    void getInitialCost() {
        InputParser input = new InputParser(1, 12.54f,false,"bottle of perfume");
        float expected = 12.54f;

        float actual = input.getInitialCost();

        assertEquals(expected,actual);
    }

    @Test
    void isItemImported() {
        InputParser input = new InputParser(1, 12.54f,true,"bottle of perfume");
        boolean expected = true;

        boolean actual = input.isItemImported();

        assertEquals(expected,actual);
    }

    @Test
    void getName() {
        InputParser input = new InputParser(1, 12.54f,true,"notebook");
        String expected = "notebook";

        String actual = input.getName();

        assertEquals(expected,actual);
    }


    @Test
    void parser() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("2 book at 12.49");
        list.add("1 music CD at 14.99");
        list.add("2 chocolate bar at 0.85");
        list.add("1 imported box of chocolates at 10.00");
        list.add("3 imported bottle of perfume at 47.50");
        list.add("1 packet of headache pills at 9.75");
        list.add("1 bottle of perfume at 18.99");
        ArrayList<InputParser> expected = new ArrayList<InputParser>();
        expected.add(new InputParser(2,12.49f,false,"book"));
        expected.add(new InputParser(1,14.99f,false,"music cd"));
        expected.add(new InputParser(2,0.85f,false,"chocolate bar"));
        expected.add(new InputParser(1,10.00f,true,"box of chocolates"));
        expected.add(new InputParser(3,47.50f,true,"bottle of perfume"));
        expected.add(new InputParser(1,9.75f,false,"packet of headache pills"));
        expected.add(new InputParser(1,18.99f,false,"bottle of perfume"));

        ArrayList<InputParser> actual=new InputParser().parser(list);

        assertArrayEquals(expected.toArray(),actual.toArray() );
    }
}