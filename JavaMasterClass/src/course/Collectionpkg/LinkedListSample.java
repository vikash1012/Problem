package course.Collectionpkg;

import java.util.LinkedList;

public class LinkedListSample {
    public static void main(String[] args) {
        LinkedList<String> myFruits=new LinkedList<>();
        myFruits.add("Mango");
        myFruits.add("Guava");
        myFruits.add("Orange");
        myFruits.addFirst("Apple");
        myFruits.add("Pomegranate");
        myFruits.add("Mango");
//        myFruits.removeLastOccurrence("Mango");
        for(String s:myFruits){
            System.out.println(s+" ");
        }


    }
}
