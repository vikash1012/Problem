package org.example.tax;

import org.example.model.LineItem;


public class TaxCalculator {
    private final static float ROUNDER=0.05f;
        public static float calculateTax(LineItem lineItem){
            if(lineItem.isExempted()&&!lineItem.isImported()){
                return 0f;
            }
            else if(lineItem.isExempted()&& lineItem.isImported()){
                return roundoff((float)0.05* lineItem.getPrice());
            }
            else if(!lineItem.isExempted()&&!lineItem.isImported()){
                return roundoff((float).1* lineItem.getPrice());
            }
            return roundoff((float).15* lineItem.getPrice());

        }
    public static float roundoff(float tax){
            return (float) (Math.ceil(tax/ROUNDER)*ROUNDER);
    }

}
