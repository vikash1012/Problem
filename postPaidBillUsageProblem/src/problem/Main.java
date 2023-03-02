package problem;
import problem.CalculateUsage;
import java.sql.SQLOutput;
import java.util.Scanner;

//25 mins std call to abc
//55 mins local call to abc
//125 mins isd call to abc
//25 local sms to abc
//50 std sms to abc

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
     int i=1;
        while(s.hasNextLine()){
            if(i==1) {

                System.out.println("\nOutput: ");
                i++;
            }
             String ans=new CalculateUsage().calculateUsage(s.nextLine());
            System.out.println(ans);
        }




    }
}