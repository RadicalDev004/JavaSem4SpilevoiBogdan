//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Helper.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Project prj1 = new Project("App", Project.Type.Practical);
        Student s1 = new Student("Bogdan", LocalDate.of(2023, 5, 20), prj1);

        System.out.println(prj1.toString());
        System.out.println(s1.toString());
    }
}