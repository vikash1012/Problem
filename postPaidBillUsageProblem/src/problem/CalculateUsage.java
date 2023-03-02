package problem;

public class CalculateUsage {
    public static float totalCost=0;
  public static int serviceUsage=0;
    public static String serviceType;
    public static StringBuilder output(float cost){
        StringBuilder str= new StringBuilder();
        if(cost<=Service.MAX_PLAN_LIMIT){
            str.append((int)cost+" ");
            str.append(Service.WITHIN_LIMIT_STRING);


        }
        else{
            str.append((int)cost+" "+Service.EXCEEDED_LIMIT_BY_RS+Math.abs((int)(Service.MAX_PLAN_LIMIT-cost)));

        }
        return str;

    }
//    public static void printOutputString(String output){
//        System.out.println(output);
//    }
    public  static String calculateUsage(String input) {
        String[] serviceString= InputParser.parseInput(input);
        if(serviceString.length==0){
            return "Input Format is Invalid";
        }
        setServiceUsage(Integer.parseInt(serviceString[0]));

        setServiceType(serviceString[1].toLowerCase()+serviceString[2].toLowerCase());
        float cost=Service.cost(CalculateUsage.serviceType,CalculateUsage.serviceUsage);
        setTotalCost(cost);
//        System.out.println(serviceUsage+" "+serviceType+" "+cost);
        String ans=output(CalculateUsage.totalCost).toString();
//        printOutputString(ans);
        return ans;




    }
    public static void setTotalCost(float cost){
        CalculateUsage.totalCost=cost;
    }
    public static void setServiceUsage(int usage){
       CalculateUsage.serviceUsage=usage;
    }
    public static void setServiceType(String type){
        CalculateUsage.serviceType=type;
    }
    public static float getTotalCost(){
        return totalCost;

    }
    public static int getServiceUsage(){
        return serviceUsage;

    }
    public static String  getServiceType(){
        return serviceType;

    }
}
