package course.MyPackageA;
import course.MypackageB.Person;
public class ClassObjSample {

    public static void main(String[] args) {

        Person alex=new Person( "Alex", 55,"Male");

        Person mia=new Person("Mia",40,"Female");

        Person james=new Person( "James",5,"Male");

        alex.sleep();
        mia.sleep();
        james.sleep();




    }
}