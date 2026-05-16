package Backend;
import java.io.Serializable;
import java.util.*;

//Student class
public class Student implements Serializable{

    //attributes
    private String stname; //Student name;
    private String regnum; //FA25-BCS-005 format
    private String program; //Computer Science 
    private ArrayList <Course> c = new ArrayList<>();
    private static int totalStudents = 0;

    //Zero Argument Constructor
    public Student() {

        stname = "Unknown";
        regnum = "No Registration Number Assigned";
        program = "Unknown";
        totalStudents++;

    }

    //Argument Constructor
    public Student (String stname, String regnum, String program) {

        setStname(stname);
        setRegnum(regnum);
        setProgram(program);
        totalStudents++;

    }

    //Setters
    public final void setStname(String stname) {

        if(!stname.isBlank()){
            this.stname = stname;
        }else{
            this.stname = "Unknown";
        }
        
    }

    public final void setRegnum(String regnum) {

        if(!regnum.isBlank()){
            this.regnum = regnum;
        }else{
            this.regnum = "No Registration Number Assigned";
        }

    }

    public final void setProgram(String program) {
        
        if(!program.isBlank()){
            this.program = program;
        }else{
            this.program = "Unknown";
        }

    }

    //Getters
    public String getStname() {
        return stname;
    }

    public String getRegnum() {
        return regnum;
    }

    public String getProgram() {
        return program;
    }
    
    public static int getTotalStudents() {
        return totalStudents;
    }
    
    public static void displayTotalStudents() {
        System.out.println("Total Students = " +totalStudents);
    }

    public ArrayList<Course> getCourseList() {
        return c;
    }
    
    //Display Info
    @Override
    public String toString() {
          return ("Student Name : " +stname+"\nRegistration Number : " +regnum+"\nProgram : " +program);
    }
    
    //Register in course 
    public void enroll(Course c1) {
        int index = c.indexOf(c1);
        if(index != -1) {

            System.out.println("Student Already Enrolled in " +c1.getCourseName());

        }else{

            c.add(c1);
            c1.register(this);
            //System.out.println("Enrollment Successful.");
        }
    }
    
    //Remove Course or Drop Course 
    public void dropCourse(Course c1) {
        int index = c.indexOf(c1);
        if(index != -1) {

            c.remove(c1);
            c1.removeStudent(this);
            //System.out.println("Course Dropped.");

        }else{

            System.out.println("Student needs to be enrolled in " +c1.getCourseName()+ " to become eligible to drop it.");
        }
    }
    
    //Number of Courses enrolled in 
    public int getNumberOfCoursesEnrolledIn() {
        return c.size();
    }
    
    //Display enrolled Courses
    public void displayEnrolledCourses() {
        System.out.println("Detail of Courses " +stname+ " is Enrolled in : ");
        for(int i=0; i<c.size(); i++) {
            System.out.println();
            System.out.println(c.get(i).toString());
            System.out.println();
        }
    }

}//end of Student Class


//same reasoning for the final keyword as 'Assignment class's