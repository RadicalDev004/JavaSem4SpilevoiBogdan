package Helper;

import java.time.LocalDate;

public class Teacher extends  Person{
    private Project[] ProposedProjects;
    private int Length, MaxLength;

    public Teacher(String name, LocalDate birthDate, int Length, Project... proposedProjects) {
        super(name, birthDate);
        MaxLength = Length;
        ProposedProjects = new Project[Length];

        System.arraycopy(proposedProjects, 0, ProposedProjects, 0, ProposedProjects.length);
        this.Length = ProposedProjects.length;
    }

    public int getLength() {
        return Length;
    }
    public void addProject(Project NewProjects) {
        if(Length >= MaxLength - 1)
            return;
        ProposedProjects[Length++] = NewProjects;
    }

    public Project popProject() {
        if(Length <= 0)
            return null;
        Project prj = ProposedProjects[Length];
        ProposedProjects[Length--] = null;
        return prj;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Teacher))
            return false;
        return ((Teacher)obj).getName().equals(getName());
    }
}
