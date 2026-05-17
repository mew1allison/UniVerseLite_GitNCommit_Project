package Frontend;

import Backend.*;

public class Runner {
    public static void main(String[] args) {
        CampusData data = new CampusData();
        data.users.add(new User("admin", "admin123", "ADMIN"));
        data.users.add(new User("Teacher", "teacher123", "TEACHER"));
        data.users.add(new User("Student", "student123", "STUDENT"));

        new LoginFrame(data);
    }
    
}//end of runner class 
