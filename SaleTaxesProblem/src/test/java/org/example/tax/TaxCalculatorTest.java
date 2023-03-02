package org.example.tax;

import org.example.model.LineItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {

    @Test
    void ShouldReturnZeroTax() {
        LineItem lineItem = new LineItem("book",2,20f,true,false);
        float expectedTax = 0f;

        float actualTax = TaxCalculator.calculateTax(lineItem);

        assertEquals(expectedTax,actualTax);
    }

    @Test
    void ShouldReturnFivePercentTax() {
        LineItem lineItem = new LineItem("book",2,20f,true,true);
        float expectedTax = 1f;

        float actualTax = TaxCalculator.calculateTax(lineItem);

        assertEquals(expectedTax,actualTax);
    }

    @Test
    void ShouldReturnTenPercentTax() {
        LineItem lineItem = new LineItem("music cd",1,20f,false,false);
        float expectedTax = 2f;

        float actualTax = TaxCalculator.calculateTax(lineItem);

        assertEquals(expectedTax,actualTax);
    }

    @Test
    void ShouldReturnFifteenPercentTax() {
        LineItem lineItem = new LineItem("bottle of perfume",1,20f,false,true);
        float expectedTax = 3f;

        float actualTax = TaxCalculator.calculateTax(lineItem);

        assertEquals(expectedTax,actualTax);
    }
    
}