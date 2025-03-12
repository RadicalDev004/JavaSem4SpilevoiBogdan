package Helper;

import java.time.LocalDate;
import java.util.*;


public class Solution {

    public static Map<Student, Project> GreedyRepartition(Teacher[] teachers, Student[] students) {

        Map<Teacher, Integer> availableSlots = new HashMap<>();
        for (Teacher teacher : teachers)
        {
            availableSlots.put(teacher, teacher.getLength());
        }

        Map<Student, Project> allocation = new HashMap<>();

        for (Student student : students) {
            for (int i = 0; i < student.getPreferenceLength(); i++)
            {
                Project preferredProject = student.getPreference(i);
                if (preferredProject == null) continue;

                Teacher proposingTeacher = findTeacher(teachers, preferredProject);
                if (proposingTeacher != null && availableSlots.get(proposingTeacher) > 0)
                {
                    allocation.put(student, preferredProject);

                    availableSlots.put(proposingTeacher, availableSlots.get(proposingTeacher) - 1);
                    break;
                }
            }
        }
        return allocation;
    }

    private static Teacher findTeacher(Teacher[] teachers, Project project) {
        for (Teacher teacher : teachers)
        {
            for (int i = 0; i < teacher.getLength(); i++)
            {
                if (teacher.popProject().equals(project))
                    return teacher;
            }
        }
        return null;
    }


    public static boolean HallsTheoremVerification(Student[] students, Project[] projects) {
        int numStudents = students.length;
        int numProjects = projects.length;


        List<List<Project>> studentPreferences = new ArrayList<>();

        for (Student student : students)
        {
            List<Project> preferences = new ArrayList<>();
            for (int i = 0; i < student.getPreferenceLength(); i++)
            {
                Project project = student.getPreference(i);
                if (project != null) {
                    preferences.add(project);
                }
            }
            studentPreferences.add(preferences);
        }

        List<Integer> subset = new ArrayList<>();

        for (int subsetSize = 1; subsetSize <= numStudents; subsetSize++) {
            if (!generateAndCheckSubsets(0, subsetSize, numStudents, subset, studentPreferences)) {
                return false;
            }
        }

        return true;
    }


    private static boolean generateAndCheckSubsets(int start, int size, int numStudents, List<Integer> subset, List<List<Project>> studentPreferences) {
        if (subset.size() == size)
        {
            return checkHallCondition(subset, studentPreferences);
        }

        for (int i = start; i < numStudents; i++)
        {
            subset.add(i);
            if (!generateAndCheckSubsets(i + 1, size, numStudents, subset, studentPreferences))
                return false;

            subset.remove(subset.size() - 1);
        }

        return true;
    }


    private static boolean checkHallCondition(List<Integer> subset, List<List<Project>> studentPreferences) {
        List<Project> uniqueProjects = new ArrayList<>();

        for (int studentIndex : subset)
        {
            for (Project project : studentPreferences.get(studentIndex)) {

                if (!uniqueProjects.contains(project))
                {
                    uniqueProjects.add(project);
                }
            }
        }

        return uniqueProjects.size() >= subset.size();
    }

    public static void TestHallTheorem()
    {
        int[] studentCounts = {10, 50, 100, 200, 500};
        int projectMultiplier = 2;

        for (int numStudents : studentCounts) {
            int numProjects = numStudents * projectMultiplier;

            Student[] students = generateRandomStudents(numStudents, numProjects);
            Project[] projects = generateRandomProjects(numProjects);

            measurePerformance(students, projects);
        }
    }

    private static void measurePerformance(Student[] students, Project[] projects)
    {
        Runtime runtime = Runtime.getRuntime();

        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();

        boolean possible = HallsTheoremVerification(students, projects);

        long endTime = System.nanoTime();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();


        System.out.println("Students: " + students.length + ", Projects: " + projects.length);
        System.out.println("Result: " + (possible ? "Feasible" : "Infeasible"));
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Memory Used: " + (memoryAfter - memoryBefore) / 1024 + " KB");
        System.out.println("-----------------------------");
    }

    private static Project[] generateRandomProjects(int numProjects) {
        Project[] projects = new Project[numProjects];
        for (int i = 0; i < numProjects; i++) {
            projects[i] = new Project("Project " + i, Project.Type.Practical);
        }
        return projects;
    }

    private static Student[] generateRandomStudents(int numStudents, int numProjects) {
        Random random = new Random();
        Student[] students = new Student[numStudents];
        Project[] allProjects = generateRandomProjects(numProjects);

        for (int i = 0; i < numStudents; i++) {
            int numPreferences = random.nextInt(3) + 1; // Each student prefers 1-3 projects
            Project[] preferences = new Project[numPreferences];

            for (int j = 0; j < numPreferences; j++) {
                preferences[j] = allProjects[random.nextInt(numProjects)];
            }

            students[i] = new Student("Student " + i, LocalDate.of(2000, 1, 1), preferences);
        }

        return students;
    }

}
