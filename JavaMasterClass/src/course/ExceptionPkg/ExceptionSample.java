package course.ExceptionPkg;

import org.junit.jupiter.api.function.Executable;

public class ExceptionSample {

    public static int add (int a, int b){
        if(a<-1000||b<-1000){
            throw new RuntimeException("Number Must be greater equals or greater than -1000");
        }
        if(a>50000||b>50000){
            throw new RuntimeException("Number Must be less than 50000");
        }
        return a+b;


    }
}
