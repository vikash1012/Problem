package course.MypackageB;
import java.sql.SQLOutput;

public class Person implements Comparable<Person> {
    public String name;
    public int age;
    public String gender;
    public Person(String name,int age,String gender){
        this.name=name;
        this.age=age;
        this.gender=gender;
    }
    public void sleep(){
        if(age<10){
            System.out.println(name+ " will sleep more than 12hrs in a day");
        }
        else if(age>=10&&age<=50){
            System.out.println(name+" will sleep less than 10hrs in a day");
        }
        else{
            System.out.println(name+" will sleep average 10hrs in a day");
        }


    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && this.name.equals(((Person) obj).name)&&this.age==(((Person) obj).age)&&this.gender.equals(((Person) obj).gender);
    }

    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.name);
    }
}
