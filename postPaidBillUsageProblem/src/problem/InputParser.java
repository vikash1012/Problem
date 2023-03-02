package problem;

public class InputParser {
    public static String[] parseInput(String input){
        String empty[]={};

        if(input==null){
            return empty;
        }
        if(input.length()==0){

            return empty;
        }
        input=input.toLowerCase();
        String[] parsedStrings = input.split(" ");
        String[] returnString = new String[3];
        if (parsedStrings.length == 6) {
            try{
                int usage=Integer.parseInt(parsedStrings[0]);

            }
            catch(NumberFormatException e){
                return empty;
            }
            if((parsedStrings[1].equals("min"))&&(parsedStrings[2].equals("local")||parsedStrings[2].equals("std")||parsedStrings[2].equals("isd"))&&parsedStrings[3].equals("call")){

                    returnString[0] = parsedStrings[0];
                    returnString[1] = parsedStrings[2];
                    returnString[2] = parsedStrings[3];
                    return returnString;
            }
           return empty;



        }
        else if(parsedStrings.length == 5){
            try{
                int usage=Integer.parseInt(parsedStrings[0]);

            }
            catch(NumberFormatException e){
                return empty;
            }
            if((parsedStrings[1].equals("local")||parsedStrings[1].equals("std")||parsedStrings[1].equals("isd"))&&(parsedStrings[2].equals("sms")))
            {
                returnString[0] = parsedStrings[0];
                returnString[1] = parsedStrings[1];
                returnString[2] = parsedStrings[2];
                return returnString;
            }
            return empty;

        }
        else{
            return empty;
        }
    }

}
