package org.example.parser;

import java.util.ArrayList;
import java.util.Objects;

// Feedback
// packages name should start with smallcase Done

public class InputParser {

    private int quantity;
    private float initialCost;
    private boolean isItemImported;
    private String name;


    public int getQuantity() {
        return quantity;
    }

    public float getInitialCost() {
        return initialCost;
    }

    public boolean isItemImported() {
        return isItemImported;
    }

    public String getName() {
        return name;
    }


    public InputParser(int quantity, float initialCost, boolean isItemImported, String name) {
        this.quantity = quantity;
        this.initialCost = initialCost;
        this.isItemImported = isItemImported;
        this.name = name;
    }

    public InputParser() {
    }

    public static ArrayList<InputParser> parser(ArrayList<String> list) {

        ArrayList<InputParser> parsedString = new ArrayList<InputParser>();

        for (String Item : list) {

            Item=Item.toLowerCase();

            String[] ItemArray = Item.split(" ");

            String initialCost = ItemArray[ItemArray.length - 1];

            // this should be boolean
            boolean itemIsImported = false;

            String name = "";

            if (ItemArray[1].equals("imported")) {
                itemIsImported = true;
            } else {
                itemIsImported = false;
            }

            if (itemIsImported == true) {
                for (int i = 2; i < ItemArray.length - 2; i++) {
                    name += ItemArray[i];
                    if (i < ItemArray.length - 3) name += " ";
                }
            } else {
                for (int i = 1; i < ItemArray.length - 2; i++) {
                    name += ItemArray[i];
                    if (i < ItemArray.length - 3) name += " ";
                }
            }

            InputParser input = new InputParser(Integer.parseInt(ItemArray[0]),Float.parseFloat(ItemArray[ItemArray.length-1]),itemIsImported, name);
            parsedString.add(input);
        }
        return parsedString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputParser that = (InputParser) o;
        return quantity == that.quantity && Float.compare(that.initialCost, initialCost) == 0 && isItemImported == that.isItemImported && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, initialCost, isItemImported, name);
    }
}
