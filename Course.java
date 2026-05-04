//Course Class

public class Course {
    
    //attributes
    private String courseName;
    private String courseID;
    private int credithours;
    
    //Zero Argument Constructor
    public Course(){
        
    }
    
    //Argument Constructor
    public Course(String courseName, String courseID, int credithours) {
        
        setCourseName(courseName);
        setCourseID(courseID);
        setCreditHours(credithours);
        
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
    
}//end of Course Class