package course.Inheritance;

import com.sun.security.jgss.GSSUtil;

public class Artist extends Human{
    public void preparePainting(){
        System.out.println(name+" is preparing for painting");
    }

    public static void main(String[] args) {
        Artist n=new Artist();
        n.name="Lucia";
        n.age=23;
        n.gender="Female";
        n.preparePainting();
        n.walk();

    }
}
