 import java.io.Serializable;
import java.util.*;
//Course Class

public class Course implements Serializable {
    
    //attributes
    private String courseName;
    private String courseID;
    private int credithours;
    private ArrayList <Assignment> a;
    private ArrayList <Student> s = new ArrayList<>();
    private static int totalCourses = 0;
    
    //Zero Argument Constructor
    public Course(){

        courseName = "Unknown";
        courseID = "UNKNOWN-000";
        credithours = 0;
        a = new ArrayList<>();
        totalCourses++;
    }
    
    //Argument Constructor
    public Course(String courseName, String courseID, int credithours) {
        
        setCourseName(courseName);
        setCourseID(courseID);
        setCreditHours(credithours);
        a = new ArrayList<>();
        totalCourses++;
        
    } 
    
    //Setters
    public final void setCourseName(String courseName) {
        
        if(!courseName.isBlank()){
            this.courseName = courseName;
        }else{
            this.courseName = null;
        }
    }
    
    public final void setCourseID(String courseID) {
        
        if(!courseID.isBlank()){
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
    public void modifyDeadline(int num) {
          boolean isfound = false;
        for(int i=0; i<a.size(); i++) {
            if(a.get(i).getAssignmentNum() == num) {
                    Scanner inp = new Scanner(System.in);
                    System.out.print("Enter Deadline = ");
                    a.get(i).setDeadline(inp.next());
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
          s.add(s1);
          System.out.println("Registration Successful.");
    }
    
    //Remove student 
    public void removeStudent(Student s1) {
          s.remove(s1);
          System.out.println("Removal Successful.");
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

    ArrayList<Student> getStudentList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}//end of Course Class