package Helper;

import java.time.LocalDate;

public class Person extends  Object{
    private String Name;
    private LocalDate BirthDate;

    public Person(String name, LocalDate birthDate) {
        Name = name;
        BirthDate = birthDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }
}
