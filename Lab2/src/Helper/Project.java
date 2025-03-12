package Helper;

public class Project extends Object {

    public enum Type {
        Theoretical,
        Practical
    }

    private Type ProjectType;
    private String Name;

    public Project(String Name, Type ProjectType)
    {
        this.Name = Name;
        this.ProjectType = ProjectType;
    }

    public Type getType()
    {
        return ProjectType;
    }
    public void setType(Type NewType)
    {
        ProjectType = NewType;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String NewName)
    {
        Name = NewName;
    }

    @Override
    public String toString()
    {
        return "Name: " + Name + ", Type: " + ProjectType.toString();
    }
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Project))
            return false;
        return ((Project)obj).getName().equals(Name);
    }

}
