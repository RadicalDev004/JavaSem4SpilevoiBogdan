package Helper;

import java.time.LocalDate;
import java.util.*;

public class Student extends Person {

    private List<Project> Preferences = new ArrayList<>();

    public Student(String Name, LocalDate birthDate, Project... ProjectPreferences)
    {
        super(Name, birthDate);
        Preferences.addAll(Arrays.asList(ProjectPreferences));
    }

    public int getPreferenceLength()
    {
        return Preferences.size();
    }
    public Project getPreference(int ind)
    {
        return ind >= Preferences.size() ? null : Preferences.get(ind);
    }
    public void addPreference(Project Preference)
    {
        Preferences.add(Preference);
    }
    public void popPreference(Project Preference)
    {
        Preferences.remove(Preferences.size() - 1);
    }


    @Override
    public String toString()
    {
        String preferences = "";
        for (var pref : Preferences)
        {
            preferences += ("[" + pref.toString() + "], ");
        }
        return "Name: " + getName() + ", Preferences: " + preferences;
    }

    @Override
    public  boolean equals(Object obj)
    {
        if(!(obj instanceof Student))
            return false;
        return ((Student)obj).getName().equals(getName());
    }
}
