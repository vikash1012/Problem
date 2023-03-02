package org.example.tax;

import org.example.model.LineItem;
import org.example.parser.InputParser;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

public class Receipt {
    private float totalTax;
    private float totalCost;
    private static final Format FORMATTER = new DecimalFormat("0.00");
    //StringBuffer
    public void printReceipt(ArrayList<LineItem> lineItems){
        for(LineItem lineItem : lineItems){
            System.out.println(lineItem.getQuantity()+(lineItem.isImported()?" imported":"")+" "+ lineItem.getName()+": "+FORMATTER.format(lineItem.getPriceIncludingTax()));
        }
        System.out.println("Sales Taxes: "+ FORMATTER.format(this.totalTax));
        System.out.println("Total: "+FORMATTER.format(this.totalCost));
    }
    public void calculateTotalTax(ArrayList<String> items){

        ArrayList<InputParser> parsedItem = InputParser.parser(items);

        ArrayList<LineItem> defineditems = new LineItem().defineItem(parsedItem);

//        for (String[] input : parsedItem) {
//            DefineItem(input[0])
//        }

        float totalTax=0f;
        float totalCost=0f;
        for(LineItem lineItem :defineditems){
            lineItem.setPriceIncludingTax(lineItem.getPrice()+TaxCalculator.calculateTax(lineItem));
            totalTax+=TaxCalculator.calculateTax(lineItem);
            totalCost+= lineItem.getPriceIncludingTax();
        }
        setTotalCost(totalCost);
        setTotalTax(totalTax);
        printReceipt(defineditems);
    }
    public void setTotalTax(float totalTax) {
        this.totalTax = totalTax;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getTotalTax() {
        return totalTax;
    }

    public float getTotalCost() {
        return totalCost;
    }
}
