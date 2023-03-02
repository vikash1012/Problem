package org.example.model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ExemptedItems {
    HashSet<String> exemptedItems = new HashSet<String>();

    public ExemptedItems() {
        this.exemptedItems.add("book");
        this.exemptedItems.add("pill");
        this.exemptedItems.add("chocolate");
    }

    public boolean isExempted(String item){
        for(String exempted: exemptedItems){
            if(item.contains(exempted)){
                return true;
            }
        }
        return false;
    }
}
