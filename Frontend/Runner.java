package Frontend;

import Backend.CampusData;
import Backend.Student;
import Backend.FileHandler;

public class Runner {
    public static void main(String[] args) {
        // 1. Create 3 dummy student objects using the argument constructor
        Student s1 = new Student("Alice Johnson", "FA25-BCS-001", "Computer Science");
        Student s2 = new Student("Bob Smith", "FA25-BCS-002", "Software Engineering");
        Student s3 = new Student("Charlie Davis", "FA25-BCS-003", "Data Science");

        // 2. Add Students to data
        CampusData data = new CampusData();
        data.students.add(s1); data.students.add(s2); data.students.add(s3);
        new MainFrame();
    }
    
}
