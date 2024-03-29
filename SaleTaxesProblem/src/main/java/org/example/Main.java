package org.example;

import org.example.tax.Receipt;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        /*Input 1:
        1 book at 12.49
        1 music CD at 14.99
        1 chocolate bar at 0.85

        Input 2:
        1 imported box of chocolates at 10.00
        1 imported bottle of perfume at 47.50

        Input 3:
        1 imported bottle of perfume at 27.99
        1 bottle of perfume at 18.99
        1 packet of headache pills at 9.75
        1 box of imported chocolates at 11.25
*/

        ArrayList<String> inputString=new ArrayList<>();

        inputString.add("1 imported bottle of perfume at 27.99");
        inputString.add("1 bottle of perfume at 18.99");
        inputString.add("1 packet of headache pills at 9.75");

        inputString.add("2 imported box of chocolates at 10.00");
        inputString.add("3 imported bottle of perfume at 47.50");

//        inputString.add("1 chocolate bar at 0.85");
        new Receipt().calculateTotalTax(inputString);

    }
}