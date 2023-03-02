package org.example.tax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void shouldReturnTotalTaxUsingSettersAndGetters() {
        Receipt receipt = new Receipt();
        receipt.setTotalTax(72.80f);
        float expected = 72.80f;

        float actual = receipt.getTotalTax();

        assertEquals(expected,actual);
    }

    @Test
    void shouldReturnTotalCostUsingSettersAndGetters() {
        Receipt receipt = new Receipt();
        receipt.setTotalCost(54.32f);
        float expected = 54.32f;

        float actual = receipt.getTotalCost();

        assertEquals(expected,actual);
    }
}