package course.Inheritance;

public class Musician extends Human{//is a relation.



    Keyboard newKey =new Keyboard(); //has a relation.


    public void playKeyboard(){
        System.out.println(name+" is playing keyboard now.");

    }
    public void speak(){
        System.out.println(name+" will speak calm!!");
    }
    public static void main(String[] args) {
        Musician n=new Musician();
        n.name="Jack";

        n.playKeyboard();
        n.newKey.playSound();
        n.walk();
        n.speak();


    }
}
