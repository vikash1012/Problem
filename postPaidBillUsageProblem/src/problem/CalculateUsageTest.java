package problem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateUsageTest {

    @Test
    void ShouldRetrunoutputFormatWhenCostWithinLimit()
    {
        float cost=150.f;
        String expectedOutput="150 Within Limit";

        String actualOutput=CalculateUsage.output(cost).toString();

        assertEquals(expectedOutput,actualOutput);
    }
    @Test
    void ShouldRetrunoutputFormatWhenCostExceedeLimit()
    {
        float cost=1440.75f;
        String expectedOutput="1440 Exceeded Limit by Rs.1140";

        String actualOutput=CalculateUsage.output(cost).toString();

        assertEquals(expectedOutput,actualOutput);
    }


    @Test
    void shouldReturnCalculateUsageWhenInputIsValid() {
        String inputString="200 min std call to ram";
        String expectedOutput="200 Within Limit";

        String actualOutput=CalculateUsage.calculateUsage(inputString);

        assertEquals(expectedOutput,actualOutput);
    }
    @Test
    void shouldReturnCalculateUsageWhenInputIsInvalidForamt(){
        String inputString="abc min 50 call to ram";
        String expectedOutput="Input Format is Invalid";

        String actualOutput=CalculateUsage.calculateUsage(inputString);

        assertEquals(expectedOutput,actualOutput);


    }
    @Test
    void shouldReturnCalculateUsageWhenInputIsInvalidDueLength(){
        String inputString="129 min local call and 200 isd sms to Ram";
        String expectedOutput="Input Format is Invalid";

        String actualOutput=CalculateUsage.calculateUsage(inputString);

        assertEquals(expectedOutput,actualOutput);


    }

    @Test
    void shouldReturnSetTotalCostAndGetTotalCost() {
        float totalcost=150f;
       float expectedTotalCost=150f;

       CalculateUsage.setTotalCost(totalcost);
       float actualTotalCost=CalculateUsage.getTotalCost();

       assertEquals(expectedTotalCost,actualTotalCost);

    }

    @Test
    void shouldReturnSetServiceUsageAndGetServiceUsage() {
        int serviceUsage=100;
        int expectedServiceUsage=100;

        CalculateUsage.setServiceUsage(serviceUsage);
        int acutalServiceUsage=CalculateUsage.getServiceUsage();

        assertEquals(expectedServiceUsage,acutalServiceUsage);
    }

    @Test
    void shouldReturnSetServiceTypeAndGetServiceType() {
        String service="localCall";
        String expectedServiceType="localCall";

        CalculateUsage.setServiceType(service);
        String acutalServiceType=CalculateUsage.getServiceType();

        assertEquals(expectedServiceType,acutalServiceType);

    }



}