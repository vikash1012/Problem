package course.Collectionpkg;

import course.MypackageB.Person;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListSample {
    public static void main(String[] args) {
        /*This List allows us to store object of different types.
        ArrayList myList= new ArrayList();
        myList.add("Ram");
        myList.add(new Person("Sita",25,"female"));
        System.out.println(((Person)myList.get(1)).gender);
         */


       /*This List is also allow to store the same types object
        ArrayList<Integer> myInt=new ArrayList();
        myInt.add(10);
        myInt.add(9);
        myInt.add(17);
        myInt.add(8);
        myInt.add(16);

        This is the way to sort all the Collection of util Package
        Collections.sort(myInt);
        for(Integer value:myInt){
            System.out.print(value+" ");
        }

        */
        ArrayList<Person> myPerson = new ArrayList<>();
        myPerson.add(new Person("Vikash", 22, "male"));
        myPerson.add(new Person("Akash", 19, "male"));
        myPerson.add(new Person("Prakash", 21, "male"));
        //Sorting of Collection.
        Collections.sort(myPerson);
        for(Person p:myPerson){
            System.out.println(p.name+" ");
        }


    }
}
