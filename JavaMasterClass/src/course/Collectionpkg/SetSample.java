package course.Collectionpkg;

import course.MypackageB.Person;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetSample {
    public static void main(String[] args) {
        //HashSet- Unordered and Unique Value:
        /*
        HashSet<String> mySet = new HashSet<>();
        mySet.add("Ram");
        mySet.add("Mohan");
        mySet.add("Geeta");
        mySet.add("Mohan");
        mySet.add("Sita");
        for (String name : mySet) {
            System.out.println(name + " ");
        }
        */
        HashSet<Person> myPerson = new HashSet<Person>();
        myPerson.add(new Person("Ram", 42, "Male"));
        myPerson.add(new Person("Mohan", 35, "Male"));
        myPerson.add(new Person("Ram", 42, "Male"));
        for (Person t : myPerson) {
            System.out.println(t.name);
        }
        System.out.println("*****************************");

        //LinkedHashSet- Ordered and Unique:
        LinkedHashSet<String> myCollection = new LinkedHashSet<>();
        myCollection.add("White Shirt");
        myCollection.add("Black Pant");
        myCollection.add("Smart Watch");
        myCollection.add("Sneaker");
        myCollection.add("Belt");
        for (String outfit : myCollection) {
            System.out.println(outfit + " ");
        }
        System.out.println("*****************************");


        //TreeSet- Ordered, Sorted and Unique:
        TreeSet<String> myCollection2 = new TreeSet<>();
        myCollection2.add("White Shirt");
        myCollection2.add("Black Pant");
        myCollection2.add("Smart Watch");
        myCollection2.add("Sneaker");
        myCollection2.add("Belt");
        for (String outfit : myCollection2) {
            System.out.println(outfit + " ");
        }


    }

}
