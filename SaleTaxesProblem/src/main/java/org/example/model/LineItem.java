package org.example.model;

import org.example.parser.InputParser;

import java.util.ArrayList;
import java.util.Objects;

// Update the name to Item Done
// Class name should represent Item
public class LineItem {

    private String name;
    private int quantity;
    private float price;
    private float priceIncludingTax;
    private boolean isExempted;
    private boolean isImported;

    public LineItem(String name, int quantity, float price, boolean isExempted, boolean isImported) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isExempted = isExempted;
        this.isImported = isImported;
    }

    public LineItem() {

    }

    // Why this is static?
    // Constructor should start with a capital letter
    public ArrayList<LineItem> defineItem(ArrayList<InputParser> items) {
        ArrayList<LineItem> lineItemList = new ArrayList<>();
        for (InputParser item : items) {
            Boolean isExempted = new ExemptedItems().isExempted(item.getName());
            lineItemList.add(new LineItem(item.getName(), item.getQuantity(), item.getInitialCost(), isExempted, item.isItemImported()));
        } return lineItemList;

    }

    public float getPriceIncludingTax() {
        return priceIncludingTax;
    }

    public void setPriceIncludingTax(float priceIncludingTax) {
        this.priceIncludingTax = priceIncludingTax;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public boolean isExempted() {
        return isExempted;
    }

    public boolean isImported() {
        return isImported;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return quantity == lineItem.quantity && Float.compare(lineItem.price, price) == 0 && isExempted == lineItem.isExempted && isImported == lineItem.isImported && Objects.equals(name, lineItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, price, isExempted, isImported);
    }
}
