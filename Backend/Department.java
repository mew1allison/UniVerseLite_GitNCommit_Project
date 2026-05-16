package Backend;

import java.util.*;

//Department class
public class Department extends AcademicUnit implements Reportable{

    private static final long serialVersionUID = 1L;

    //data member
    private ArrayList<Course> courses;
    
    public Department() {
        super();
        courses = new ArrayList<>();
    }
    
    public Department(String name, String location, int entityID) {
        super(name, location, entityID);
        courses = new ArrayList<>();
    }
    
    //Add courses
    public void addCourse(Course c) {
        int index = courses.indexOf(c);

        if(index != -1) {
            System.out.println("Course Already Added!");
        }else{
            courses.add(c);
            System.out.println("Course Added Successfully");
        }
 
    }
    
    //Remove Courses 
    public void removeCourse(Course c) {

        int index = courses.indexOf(c);
        if(index != -1) {
            courses.remove(c);
            System.out.println("Course Removed Successfully");
        }else{
            System.out.println("Course Removal requires Course Addition. This course isnt added.");
        }

    }
    
    //total courses in the department 
    public int getTotalCoursesinDep() {
        return courses.size();
    }
    
    //total students enrolled in the department
    public int totalStudentsinDep() {
        int totalstudents = 0;
        boolean counted;
        
        //Goes through the course present at index in the course list 
        for(int i=0; i<courses.size(); i++) {
            //Obtains the student enrolled list of that course and stores it in the enrolled student arraylist 
            ArrayList<Student> enrolled = courses.get(i).getStudentList();
            //Loops through each student enrolled in that course and sets the boolean false for all of them telling the system that they havent been counted means they are unique students with who hadnt been counted before
            for(int j=0; j<enrolled.size(); j++){
                counted = false;
                
                //checks the course list of that course to see if that student was counted before or not if counted the boolean becomes true
                for(int k=0; k<i; k++){
                    if(courses.get(k).getStudentList().contains(enrolled.get(j))) {
                        counted = true;
                    }
                }
                
                //if the student wasnt counted before then it is added to the total student otherwise it isnt added.
                if(!counted){
                    totalstudents++;
                }
            }
        }
        return totalstudents;
    }
    
    @Override
    public String toString() {
        return super.toString() + ("\nTotal Courses Offered = " +getTotalCoursesinDep()+"\nTotal Students Enrolled in Department = " +totalStudentsinDep());
    }
    
    @Override
    public double calculateOperationalCost() {
        double cost = 0;
        cost += totalStudentsinDep() * 500; // students from courses
        for(int i = 0; i < equipment.size(); i++) {
            cost += equipment.get(i).getPrice(); // equipment added directly
        }
        return cost;
    }

    @Override
    public void generateReport() {
        System.out.println("DEPARTMENT PERFORMANCE REPORT");
        System.out.println(this.toString());
        System.out.println("Total Equipments : " +equipment.size());
        System.out.println("Operational Cost : " +calculateOperationalCost());
    }

}//end of Department class 