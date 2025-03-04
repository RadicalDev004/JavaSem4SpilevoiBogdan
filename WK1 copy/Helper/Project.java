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
    
    public Type GetType() 
    {
        return ProjectType;
    }
    public void SetType(Type NewType)
    {
        ProjectType = NewType;
    }
    
    public String GetName()
    {
        return Name;
    }

    public void SetName(String NewName)
    {
        Name = NewName;
    }
    
    @Override
    public String toString()
    {
        return "Name: " + Name + ", Type: " + ProjectType.toString();
    }
}
