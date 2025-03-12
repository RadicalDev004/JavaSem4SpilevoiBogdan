package Helper;

public class Problem {
    private Person[] PersonsInvolved;
    int MaxLength, Length;

    public Problem(int maxLength, Person... personsInvolved) {
        MaxLength = maxLength;
        PersonsInvolved = new Person[personsInvolved.length];
        System.arraycopy(personsInvolved, 0, PersonsInvolved, 0, PersonsInvolved.length);
        this.Length = PersonsInvolved.length;
    }

    public void addPerson(Person person) {
        for(var pers : PersonsInvolved) {
            if(pers.equals(person))
                return;
        }
        if(Length >= MaxLength - 1)
            return;

        PersonsInvolved[Length++] = person;
    }

    public void showInvolved()
    {
        for(var pers : PersonsInvolved) {
            System.out.println(pers.toString());
        }
    }

    public Person[] getPersonsInvolved() {
        return PersonsInvolved;
    }

    public Teacher[] getTeachers()
    {
        Teacher[] Teachers = new Teacher[PersonsInvolved.length];
        for(var pers : PersonsInvolved) {
            if(pers instanceof Teacher)
                Teachers[PersonsInvolved.length-1] = (Teacher)pers;
        }
        return Teachers;
    }
    public Student[] getStudents()
    {
        Student[] Students = new Student[PersonsInvolved.length];
        for(var pers : PersonsInvolved) {
            if(pers instanceof Student)
                Students[PersonsInvolved.length-1] = (Student)pers;
        }
        return Students;
    }


}
