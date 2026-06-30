package Backend;
import java.io.Serializable;
import java.util.*;

//Course Class

public class Course implements Serializable, Schedulable {

    private static final long serialVersionUID = 1L;

    //attributes
    private String courseName;
    private String courseID;
    private int credithours;
    private ArrayList <Assignment> a;
    private ArrayList <Student> s = new ArrayList<>();
    private static int totalCourses = 0;
    private Classroom cr; //this will have the classroom assigned using schedule and reschedule methods
    private String timeslot; //this will contain the timeslot
    private ArrayList<Classroom> allclassrooms;
    
    //Zero Argument Constructor
    public Course(){

        courseName = "Unknown";
        courseID = "UNKNOWN-000";
        credithours = 0;
        a = new ArrayList<>();
        totalCourses++;
    }
    
    //Argument Constructor
    public Course(String courseName, String courseID, int credithours, ArrayList <Classroom> c1) {
        
        setCourseName(courseName);
        setCourseID(courseID);
        setCreditHours(credithours);
        a = new ArrayList<>();
        totalCourses++;
        this.allclassrooms = c1;
    } 
    
    //Setters
    public final void setCourseName(String courseName) {
        
        if(courseName != null && !courseName.isBlank()){
            this.courseName = courseName;
        }else{
            this.courseName = null;
        }
    }
    
    public final void setCourseID(String courseID) {
        
        if(courseID != null && !courseID.isBlank()){
            this.courseID = courseID;
        }else{
            this.courseID = null;
        }
    }
    
    public final void setCreditHours(int credithours) {
        
        if(credithours > 0){
            this.credithours = credithours;
        }else{
            this.credithours = 0;
        }
    }
    
    
    //Getters
    public String getCourseID(){
        return courseID;
    }
    
    public String getCourseName(){
        return courseName;
    }
    
    public int getCreditHours(){
        return credithours;
    }
    
    public ArrayList<Assignment> getAssignment() {
        return a;
    }
    
    //upload Assignment
    public void uploadAssignment(Assignment a) {
        this.a.add(a);
        System.out.println("Assignment Uploaded Successfully!");
    }
    
    //modify Deadline
    public void modifyDeadline(int num, String newDeadline) {
          boolean isfound = false;
        for(int i=0; i<a.size(); i++) {
            if(a.get(i).getAssignmentNum() == num) {
                    a.get(i).setDeadline(newDeadline);
                    System.out.println("Deadline Modified");
                    isfound = true;
            }      
        }
        if(!isfound){
              System.out.println("Could not find Assignment");
        }
        
    }
    
    //remove Assignment
    public void removeAssignment(int num) {
        boolean isfound = false;
        for(int i=0; i<a.size(); i++) {
            if(a.get(i).getAssignmentNum() == num) {
                  this.a.remove(a.get(i));
                  System.out.println("Assignment Removed Successfully.");
                  isfound = true;
            }      
        }
        if(!isfound){
              System.out.println("Could not find Assignment");
        }
          
    }
    
    //Display assignment
    public void displayAssignmentDetails(int num) {
          boolean isfound = false;
          for(int i=0; i<a.size(); i++) {
                  if(a.get(i).getAssignmentNum() == num) {
                        System.out.println(a.get(i).toString());
                        isfound = true;
                  } 
            }
            if(!isfound){
              System.out.println("Could not find Assignment");
            }
    }
    
    public int totalAssignments() {
          return a.size();
    }
    
    //Display Course Info 
    @Override
    public String toString() {
          return ("Course Details :" +"\nCourse Name : " +courseName+"\nCourse ID : " +courseID+"\nCredit Hours : " +credithours+"\nTotal Assignments : " +a.size()+"\nTotal Students Enrolled : " +s.size());
    }
    
    //DisplayAllAssignments
    public void displayAll() {
          for(int i=0; i< a.size(); i++) {
                System.out.println(a.get(i).toString());
                System.out.println();
          }
    }
    
    //Add Student (Register student in a course)
    public void register(Student s1) {

        int index = s.indexOf(s1);
        if(index != -1) {
            System.out.println("Student Already Enrolled.");
        }else{
            s.add(s1);
            System.out.println("Registration Successful.");
        }
    }
    
    //Remove student 
    public void removeStudent(Student s1) {

        int index = s.indexOf(s1);
        if(index != -1) {
            s.remove(s1);
            System.out.println("Removal Successful.");
            
        }else{
            System.out.println("Could not Remove Student as Student isn't Enrolled.");
        }
    }
    
    //Number of students enrolled in this Course
    public int getNumberOfEnrolledStudents() {
          return s.size();
    }
    
    //Display students enrolled in this Course
    public void displayEnrolledStudents() {
          System.out.println("Details of Students Enrolled in " +courseName);
          for(int i=0; i<s.size(); i++) {
                System.out.println();
                System.out.println(s.get(i).toString());
                System.out.println();
          }
    }
    
    //Getter for total courses
    public static int getTotalCourses() {
          return totalCourses;
    }
    
    public static void displayTotalCourses () {
        System.out.println("Total Courses = " +totalCourses);
    }

    public ArrayList<Student> getStudentList() {
        return s;
    }

    public final void setClassroom(Classroom c){
        this.cr = c;
    }
    
    public Classroom getAssignedClassroom() {
        return cr;
    }
    
    public String getTimeSlot() {
        return timeslot;
    }
    
    public final void setTime(String t) {
        if(t != null && !t.isBlank()) {
            this.timeslot = t;
        }else{
            this.timeslot = null;
        }
    }
    
    public void scheduleClass() {
        
        String[] times = {"8:30 - 10:00", "10:00 - 11:30", "11:30 - 1:00", "1:00 - 2:30", "2:30 - 4:00", "4:00 - 5:30" };
        for(int i=0; i<allclassrooms.size(); i++) {
            if(allclassrooms.get(i).getisAvailable() && allclassrooms.get(i).gettotalCapacity() >= s.size()) {
                for(int j=0; j<times.length; j++) {
                    if(!allclassrooms.get(i).getTimeSlotsBooked().contains(times[j])) {
                        setTime(times[j]);
                        setClassroom(allclassrooms.get(i));
                        allclassrooms.get(i).getTimeSlotsBooked().add(times[j]);
                        System.out.println("Class Assigned!");
                        return;
                    }
                }
            }
        }
        
        System.out.println("No Classroom and TimeSlot Available");
        
    }
    
    @Override
public void generateSchedule() {
    if (getAssignedClassroom() == null) {
        System.out.println("Schedule cannot be generated because no classroom has been assigned.");
        return;
    }

    System.out.println("Course Class Schedule");
    System.out.println("Course Name : " + getCourseName());
    System.out.println("Course ID : " + getCourseID());
    System.out.println("Credit Hours : " + getCreditHours());
    System.out.println("Classroom : " + getAssignedClassroom().getName());
    System.out.println("Classroom Location : " + getAssignedClassroom().getLocation());
    System.out.println("Class TimeSlot : " + getTimeSlot());
}
    
    //avaialability needs to be set to false first of the classroom 
    //a reschedule method if a classroom undergoes some maintenance and has availability set to false
    public void rescheduleClasses(Classroom classroom, ArrayList<Course> c) {
        if(classroom.getisAvailable() == false) {
            for(int i=0; i<c.size(); i++) {
                if(c.get(i).getAssignedClassroom() == classroom) {
                    c.get(i).scheduleClass();
                }
            }
        }
    }
    
}//end of Course Class
