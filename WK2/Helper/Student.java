package Helper;

import java.util.*;

public class Student extends Object {
    
    private String Name;
    List<Project> Preferences = new ArrayList<>();
    
    public Student(String Name, Project... ProjectPreferences)
    {
        this.Name = Name;
        Preferences.addAll(Arrays.asList(ProjectPreferences));
    }
    
    public Project GetPreference(int ind) 
    {
        return ind >= Preferences.size() ? null : Preferences.get(ind);
    }
    public void AddPreference(Project Preference)
    {
        Preferences.add(Preference);
    }
    public void PopPreference(Project Preference)
    {
        Preferences.remove(Preferences.size() - 1);
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
        String preferences = "";
        for (var pref : Preferences)
        {
            preferences += ("[" + pref.toString() + "], ");
        }
        return "Name: " + Name + ", Preferences: " + preferences;
    }
}
