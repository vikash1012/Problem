package problem;

import java.util.Locale;

public class Service {
    public static final String WITHIN_LIMIT_STRING = "Within Limit";
    public static final String EXCEEDED_LIMIT_BY_RS = "Exceeded Limit by Rs.";

    // max limit of usage
    public static float MAX_PLAN_LIMIT = 300f;
    public static float cost(String type,int usage){
        if(type.equals("localcall")){
            return (0.50f*usage);
        }
        else if(type.equals("stdcall")){
//            System.out.println(usage+" "+1.0f*usage);
            return (float) (1.0f*usage);
        } else if (type.equals("isdcall")) {

            return 12.0f*usage;
        }
        else if(type.equals("localsms")){
            return 0.25f*usage;
        }
        else if(type.equals("stdsms")){
            return .50f*usage;
        }
            return 5.0f * usage;

    }
}
