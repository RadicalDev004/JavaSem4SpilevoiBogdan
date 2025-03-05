
import Helper.*;

public class proj2 {
    public static void main(String[] args) {
        Project prj1 = new Project("App", Project.Type.Practical);
        Student s1 = new Student("Bogdan", prj1);
        
        System.out.println(prj1.toString());
         System.out.println(s1.toString());
    }
}