package problem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void shouldReturnCostWhenServiceTypeIsLocalCall() {
        String serviceType="localcall";
        int serviceUsage=150;
        float expectedCost=75f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }
    @Test
    void shouldReturnCostWhenServiceTypeIsStdCall(){
        String serviceType="stdcall";
        int serviceUsage=220;
        float expectedCost=220f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }
    @Test
    void shouldReturnCostWhenServiceTypeIsIsdCall(){
        String serviceType="isdcall";
        int serviceUsage=120;
        float expectedCost=1440f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }
    @Test
    void shouldReturnCostWhenServiceTypeIsLocalSms(){
        String serviceType="localsms";
        int serviceUsage=150;
        float expectedCost=37.5f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }
    @Test
    void shouldReturnCostWhenServiceTypeIsStdSms(){
        String serviceType="stdsms";
        int serviceUsage=170;
        float expectedCost=85f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }
    @Test
    void shouldReturnCostWhenServiceTypeIsIsdSms(){
        String serviceType="isdsms";
        int serviceUsage=150;
        float expectedCost=750f;

        float acutalcost=Service.cost(serviceType,serviceUsage);

        assertEquals(expectedCost,acutalcost);

    }

}